package model;

import controller.UserController;
import model.categories.Category;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matcher {
    private final Session session;
    private UserController userController;
    // Double arvo on yhteensopivuus-prosentti. Periaatteessa voi olla useampi paras match, niin tallennetaan hashmappiin
    private final HashMap<User, Double> topMatches;
    private String debugUserName;

    public Matcher(Session session) {
        this.session = session;
        userController = new UserController();
        topMatches = new HashMap<>();
    }

    // Kutsu kun käyttäjä on vahvistanut valintansa GUIssa
    public void matchParticipant() {
        double compatibility;
        double currentHighestCompatibility = 0;
        double maxPotential;
        double increment;
        List<Category> pMatchInterests;

        // check if session participant is null
        Participant toMatch = session.getParticipant();
        if (toMatch == null) {
            System.err.println("ERROR: Session participant is null.");
            return;
        }

        // check if there are any potential matches
        List<User> potentialMatches = userController.displayAllUsers();
        if (potentialMatches.isEmpty()) {
            System.err.println("ERROR: No potential matches found.");
            return;
        }

        // check if session participant has any interests
        List<Category> participantInterests = session.getParticipantInterests();
        if (participantInterests.isEmpty()) {
            System.err.println("ERROR: Session participant has no interests.");
            return;
        }

        System.out.println("DEBUG: Matching for participant " + toMatch.getDisplayName("en"));
        System.out.println("DEBUG: Participant interests: " + participantInterests);

        for (User u: potentialMatches) {
            if (u.equals(toMatch)) continue; // exclude participant from matches

            System.out.println("DEBUG: Checking user: " + u.getId());

            pMatchInterests = u.getInterests();
            if (pMatchInterests.isEmpty()) continue; // skip if user has no interests

            compatibility = 0;
            maxPotential = 100;
            increment = (double) 100 / pMatchInterests.size();

            for (Category interest: session.getParticipantInterests()) {
                if (pMatchInterests.contains(interest)) {
                    compatibility += increment;
                    System.out.println("DEBUG: Match found for interest: " + interest + " -> compatibility: " + compatibility);
                }
                else {
                    maxPotential -= increment;
                    System.out.println("DEBUG: No match found for interest: " + interest + " -> max potential: " + maxPotential);
                }
                if (maxPotential < currentHighestCompatibility) {
                    System.out.println("DEBUG: Skipping " + u.getId() + " due to low potential.");
                    break;
                }
            }
            compatibility = roundToTwoDecimalPlaces(compatibility);
            System.out.println("DEBUG: Compatibility for " + u.getId() + ": " + compatibility);

            if (compatibility > currentHighestCompatibility) {
                topMatches.clear();
                topMatches.put(u, compatibility);
                currentHighestCompatibility = compatibility;
                System.out.println("DEBUG: New highest compatibility: " + compatibility);
            } else if (compatibility == currentHighestCompatibility) {
                topMatches.put(u, compatibility);
                System.out.println("DEBUG: Added " + u.getId() + " to top matches.");
            }
        }
        if (currentHighestCompatibility == 0) {
            topMatches.clear();
            System.out.println("DEBUG: No matches found.");
        }
        if (topMatches.isEmpty()) {
            System.err.println("ERROR: No top matches found in Matcher matchParticipant()");
        }
    }

    public Session getSession() {
        return session;
    }

    public HashMap<User, Double> getTopMatches() {
        return topMatches;
    }

    // For testing
    public UserController getUserController() {
        return userController;
    }

    // For testing
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
