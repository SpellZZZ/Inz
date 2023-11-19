package org.example.service.dbService;

import jakarta.transaction.Transactional;
import org.example.dao.TruckTrailerDAO;
import org.example.model.Truck_Trailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckTrailerDBServiceImpl implements TruckTrailerDBService {

    @Autowired
    private TruckTrailerDAO truckTrailerDAO;

    @Override
    @Transactional
    public List<Truck_Trailer> getTruckTrailers() {
        return truckTrailerDAO.getTruckTrailers();
    }

    @Override
    @Transactional
    public void saveTruckTrailer(Truck_Trailer truckTrailer) {
        truckTrailerDAO.saveTruckTrailer(truckTrailer);
    }

    @Override
    @Transactional
    public Truck_Trailer getTruckTrailerById(int id) {
        return truckTrailerDAO.getTruckTrailerById(id);
    }

    @Override
    @Transactional
    public void deleteTruckTrailer(int id) {
        truckTrailerDAO.deleteTruckTrailer(id);
    }

    @Override
    @Transactional
    public void updateTruckTrailer(Truck_Trailer truckTrailer) {
        truckTrailerDAO.updateTruckTrailer(truckTrailer);
    }

    @Override
    @Transactional
    public Truck_Trailer findTruckTrailerByTruckIdAndTrailerId(int truckId, int trailerId) {
        return truckTrailerDAO.findTruckTrailerByTruckIdAndTrailerId(truckId, trailerId);
    }
}