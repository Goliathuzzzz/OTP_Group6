package service;

import dao.MatchDao;
import exception.DaoException;
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
        try {
            dao.persist(match);
        } catch (DaoException e) {
            System.out.println("Error saving match: " + e.getMessage());
        }
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
        try {
            dao.update(match);
        } catch (DaoException e) {
            System.out.println("Error updating match: " + e.getMessage());
        }
    }

    public void deleteMatch(Match match) {
        try {
            dao.delete(match);
        } catch (DaoException e) {
            System.out.println("Error deleting match: " + e.getMessage());
        }
    }

    public void deleteAllMatches() {
        try {
            dao.deleteAll();
        } catch (DaoException e) {
            System.out.println("Error deleting all matches: " + e.getMessage());
        }
    }

    // For tests
    public void setEm(EntityManager em) {
        dao.setEm(em);
    }
}
