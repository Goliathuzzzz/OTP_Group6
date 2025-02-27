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
        List<User> potentialMatches = userController.displayAllUsers();
        List<Category> pMatchInterests;
        for (User u: potentialMatches) {
            pMatchInterests = groupMatchInterests(u);
            compatibility = 0;
            maxPotential = 100;
            increment = (double) 100 / pMatchInterests.size();
            for (Category interest: session.getParticipantInterests()) {
                if (pMatchInterests.contains(interest)) {
                    compatibility += increment;
                }
                else {
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
    }

    public Session getSession() {
        return session;
    }

    private List<Category> groupMatchInterests(User user) {
        List<Category> interests = new ArrayList<>();
        interests.addAll(user.getAnimalInterests());
        interests.addAll(user.getFoodInterests());
        interests.addAll(user.getHobbiesInterests());
        interests.addAll(user.getSportsInterests());
        interests.addAll(user.getScienceInterests());
        return interests;
    }

    // Palauttaa käyttäjän parhaat matchit
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
