package org.example.dao;

import org.example.model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelDAOImpl implements ModelDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Model> getModels() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Model", Model.class).list();
    }

    @Override
    public void saveModel(Model model) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(model);
    }

    @Override
    public Model getModel(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Model.class, id);
    }

    @Override
    public void deleteModel(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Model model = currentSession.get(Model.class, id);
        currentSession.remove(model);
    }

    @Override
    public Model getModelByName(String model_name) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM Model m WHERE m.model_name = :model_name";
        Query<Model> query = currentSession.createQuery(hql, Model.class);
        query.setParameter("model_name", model_name);
        List<Model> models = query.getResultList();
        return models.isEmpty() ? null : models.get(0);
    }

    @Override
    public void updateModel(Model model) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(model);
    }
}