package dao;

import exception.DaoException;
import java.util.List;

public interface IDao<Object> {
    void persist(Object object) throws DaoException;

    Object find(int id);

    List<Object> findAll();
    void update(Object object) throws DaoException;

    void delete(Object object) throws DaoException;

    void deleteAll() throws DaoException;
}
