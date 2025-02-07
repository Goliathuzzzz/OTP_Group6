package model;

import model.categories.Category;
import service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matcher {
    private final Session session;
    private final UserService userService;
    // Double arvo on yhteensopivuus-prosentti. Periaatteessa voi olla useampi paras match, niin tallennetaan hashmappiin
    private final HashMap<Double, User> topMatches;

    public Matcher(Session session) {
        this.session = session;
        userService = new UserService();
        topMatches = new HashMap<>();
    }

    // Kutsu kun käyttäjä on vahvistanut valintansa GUIssa
    public void matchParticipant() {
        double compatibility;
        double currentHighestCompatibility = 0;
        double maxPotential;
        double increment;
        List<User> potentialMatches = userService.getAllUsers();
        List<Category> pMatchInterests;
        Participant toMatch = session.getParticipant();
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
            if (compatibility > currentHighestCompatibility) {
                topMatches.clear();
                topMatches.put(compatibility, u);
                currentHighestCompatibility = compatibility;
            } else if (compatibility == currentHighestCompatibility) {
                topMatches.put(compatibility, u);
            }
        }
        for (Map.Entry<Double, User> entry: topMatches.entrySet()) {
            new Match(toMatch, entry.getValue(), entry.getKey());
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

    public HashMap<Double, User> getTopMatches() {
        return topMatches;
    }
}
