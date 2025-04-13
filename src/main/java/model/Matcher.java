package model;

import controller.UserController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import model.categories.Category;

public class Matcher {
    private final Session session;
    // Double arvo on yhteensopivuus-prosentti.
    // Periaatteessa voi olla useampi paras match, niin tallennetaan hashmappiin
    private final HashMap<User, Double> topMatches;
    private UserController userController;

    public Matcher(Session session) {
        this.session = session;
        userController = new UserController();
        topMatches = new HashMap<>();
    }

    // Kutsu kun käyttäjä on vahvistanut valintansa GUIssa
    public void matchParticipant() {
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

        double compatibility;
        double currentHighestCompatibility = 0;
        double maxPotential;
        double increment;
        List<Category> potentialMatchInterests;

        for (User u : potentialMatches) {
            if (u.equals(toMatch)) {
                continue; // exclude participant from matches

            }

            potentialMatchInterests = u.getInterests();
            if (potentialMatchInterests.isEmpty()) {
                continue; // skip if user has no interests

            }
            compatibility = 0;
            maxPotential = 100;
            increment = (double) 100 / potentialMatchInterests.size();

            for (Category interest : session.getParticipantInterests()) {
                if (potentialMatchInterests.contains(interest)) {
                    compatibility += increment;
                } else {
                    maxPotential -= increment;
                }
                if (maxPotential < currentHighestCompatibility) {
                    break;
                }
            }
            compatibility = roundToTwoDecimalPlaces(compatibility);

            if (compatibility > currentHighestCompatibility) {
                topMatches.clear();
                topMatches.put(u, compatibility);
                currentHighestCompatibility = compatibility;
            } else if (compatibility == currentHighestCompatibility) {
                topMatches.put(u, compatibility);
            }
        }
        if (currentHighestCompatibility == 0) {
            topMatches.clear();
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
