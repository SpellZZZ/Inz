package org.example.service.dbService;

import org.example.model.Truck_Trailer;

import java.util.List;

public interface TruckTrailerDBService {
    List<Truck_Trailer> getTruckTrailers();

    void saveTruckTrailer(Truck_Trailer truckTrailer);

    Truck_Trailer getTruckTrailerById(int id);

    void deleteTruckTrailer(int id);

    void updateTruckTrailer(Truck_Trailer truckTrailer);

    Truck_Trailer findTruckTrailerByTruckIdAndTrailerId(int truckId, int trailerId);
}