package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.model.Truck_Trailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckTrailerDAOImpl implements TruckTrailerDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Truck_Trailer> getTruckTrailers() {
        TypedQuery<Truck_Trailer> query = entityManager.createQuery("SELECT tt FROM Truck_Trailer tt", Truck_Trailer.class);
        return query.getResultList();
    }

    @Override
    public void saveTruckTrailer(Truck_Trailer truckTrailer) {
        entityManager.persist(truckTrailer);
    }

    @Override
    public Truck_Trailer getTruckTrailerById(int id) {
        return entityManager.find(Truck_Trailer.class, id);
    }

    @Override
    public void deleteTruckTrailer(int id) {
        Truck_Trailer truckTrailer = getTruckTrailerById(id);
        if (truckTrailer != null) {
            entityManager.remove(truckTrailer);
        }
    }

    @Override
    public void updateTruckTrailer(Truck_Trailer truckTrailer) {
        entityManager.merge(truckTrailer);
    }

    @Override
    public Truck_Trailer findTruckTrailerByTruckIdAndTrailerId(int truckId, int trailerId) {
        TypedQuery<Truck_Trailer> query = entityManager.createQuery(
                "SELECT tt FROM Truck_Trailer tt WHERE tt.truck.id = :truckId AND tt.trailer.id = :trailerId",
                Truck_Trailer.class);
        query.setParameter("truckId", truckId);
        query.setParameter("trailerId", trailerId);

        List<Truck_Trailer> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Truck_Trailer findByTruckId(int truckId) {
        return null;
    }

    @Override
    public Truck_Trailer findByTrailerId(int trailerId) {
        return null;
    }
}
