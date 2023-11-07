package org.example.service.dbService;

import org.example.model.Model;

import java.util.List;

public interface ModelDBService {
    List<Model> getModels();

    void saveModel(Model model);

    Model getModel(int id);

    void deleteModel(int id);

    Model getModelByName(String model_name);

    void updateModel(Model model);
}