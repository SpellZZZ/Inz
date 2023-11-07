package org.example.service.dbService;

import jakarta.transaction.Transactional;
import org.example.dao.ModelDAO;
import org.example.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelDBServiceImpl implements ModelDBService {

    @Autowired
    private ModelDAO modelDAO;

    @Override
    @Transactional
    public List<Model> getModels() {
        return modelDAO.getModels();
    }

    @Override
    @Transactional
    public void saveModel(Model model) {
        modelDAO.saveModel(model);
    }

    @Override
    @Transactional
    public Model getModel(int id) {
        return modelDAO.getModel(id);
    }

    @Override
    @Transactional
    public void deleteModel(int id) {
        modelDAO.deleteModel(id);
    }

    @Override
    @Transactional
    public Model getModelByName(String model_name) {
        return modelDAO.getModelByName(model_name);
    }

    @Override
    @Transactional
    public void updateModel(Model model) {
        modelDAO.updateModel(model);
    }
}