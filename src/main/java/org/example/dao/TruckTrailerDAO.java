package org.example.dao;

import org.example.model.Truck_Trailer;

import java.util.List;

public interface TruckTrailerDAO {
    List<Truck_Trailer> getTruckTrailers();

    void saveTruckTrailer(Truck_Trailer truckTrailer);

    Truck_Trailer getTruckTrailerById(int id);

    void deleteTruckTrailer(int id);

    void updateTruckTrailer(Truck_Trailer truckTrailer);

    Truck_Trailer findTruckTrailerByTruckIdAndTrailerId(int truckId, int trailerId);

    Truck_Trailer findByTruckId(int truckId);
    Truck_Trailer findByTrailerId(int trailerId);

}