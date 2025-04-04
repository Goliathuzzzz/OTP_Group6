package controller;

import context.GUIContext;
import jakarta.persistence.EntityManager;
import model.Match;
import model.Participant;
import service.MatchService;
import java.util.List;

public class MatchController {

    private final MatchService matchService;
    private final GUIContext context = GUIContext.getInstance();

    public MatchController() {
        matchService = new MatchService();
    }

    public void matchParticipants(Participant participant1, Participant participant2, double compatibility) {
        matchService.addMatch(new Match(participant1, participant2, compatibility));
        System.out.println("Participants matched successfully");
    }

    public Match displayMatch(int id) {
        Match match = matchService.findMatchById(id);
        if (match != null) {
            System.out.println("Match found: " + match.getParticipant1().getDisplayName(context.getLanguage()) + " + " + match.getParticipant2().getDisplayName(context.getLanguage()));
        } else {
            System.out.println("Match not found");
        }
        return match;
    }

    public List<Match> displayAllMatches() {
        List<Match> matches = matchService.findAllMatches();
        for (Match m: matches) {
            System.out.println(m.getParticipant1().getDisplayName(context.getLanguage()) + " + " + m.getParticipant2().getDisplayName(context.getLanguage()));
        }
        return matches;
    }

    public List<Match> displayAllByParticipant(Participant participant) {
        List<Match> matches = matchService.findByParticipant(participant);
        for (Match m: matches) {
            System.out.println(m.getParticipant1().getDisplayName(context.getLanguage()) + " + " + m.getParticipant2().getDisplayName(context.getLanguage()));
        }
        return matches;
    }

    public void updateMatch(Match match) {
        matchService.updateMatch(match);
        System.out.println("Match updated successfully");
    }

    public void deleteMatch(Match match) {
        matchService.deleteMatch(match);
        System.out.println("Match deleted successfully");
    }

    public void deleteAll() {
        matchService.deleteAllMatches();
        System.out.println("All matches deleted");
    }

    // For testing
    public void setEm(EntityManager em) {
        matchService.setEm(em);
    }
}
