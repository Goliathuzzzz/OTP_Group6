package service;

import dao.MatchDao;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.Match;
import model.Participant;

public class MatchService {
    private final MatchDao dao;

    public MatchService() {
        dao = new MatchDao();
    }

    public void addMatch(Match match) {
        dao.persist(match);
    }

    public Match findMatchById(int id) {
        return dao.find(id);
    }

    public List<Match> findAllMatches() {
        return dao.findAll();
    }

    public List<Match> findByParticipant(Participant participant) {
        return dao.findByParticipant(participant);
    }

    public void updateMatch(Match match) {
        dao.update(match);
    }

    public void deleteMatch(Match match) {
        dao.delete(match);
    }

    public void deleteAllMatches() {
        dao.deleteAll();
    }

    // For tests
    public void setEm(EntityManager em) {
        dao.setEm(em);
    }
}
