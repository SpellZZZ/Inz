package org.example.service.dbService;

import org.example.model.Company;
import org.example.model.Trailer;
import org.example.model.Truck;

import java.util.List;

public interface TrailerDBService {
    List<Trailer> getTrailers();

    void saveTrailer(Trailer trailer);

    Trailer getTrailer(int id);

    void deleteTrailer(int id);

    void updateTrailer(Trailer trailer);
    List<Trailer> getTrailerByCompany(Company company);
}