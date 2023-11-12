package org.example.dao;

import org.example.model.Trailer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrailerDAOImpl implements TrailerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Trailer> getTrailers() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Trailer", Trailer.class).list();
    }

    @Override
    public void saveTrailer(Trailer trailer) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(trailer);
    }

    @Override
    public Trailer getTrailer(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Trailer.class, id);
    }

    @Override
    public void deleteTrailer(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Trailer trailer = currentSession.get(Trailer.class, id);
        currentSession.remove(trailer);
    }

    @Override
    public void updateTrailer(Trailer trailer) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(trailer);
    }
}
