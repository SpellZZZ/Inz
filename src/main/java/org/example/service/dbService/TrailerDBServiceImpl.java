package org.example.service.dbService;

import jakarta.transaction.Transactional;
import org.example.dao.TrailerDAO;
import org.example.model.Trailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailerDBServiceImpl implements TrailerDBService {

    @Autowired
    private TrailerDAO trailerDAO;

    @Override
    @Transactional
    public List<Trailer> getTrailers() {
        return trailerDAO.getTrailers();
    }

    @Override
    @Transactional
    public void saveTrailer(Trailer trailer) {
        trailerDAO.saveTrailer(trailer);
    }

    @Override
    @Transactional
    public Trailer getTrailer(int id) {
        return trailerDAO.getTrailer(id);
    }

    @Override
    @Transactional
    public void deleteTrailer(int id) {
        trailerDAO.deleteTrailer(id);
    }

    @Override
    @Transactional
    public void updateTrailer(Trailer trailer) {
        trailerDAO.updateTrailer(trailer);
    }
}