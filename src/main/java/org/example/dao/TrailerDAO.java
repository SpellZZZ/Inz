package org.example.dao;

import org.example.model.Company;
import org.example.model.Trailer;
import org.example.model.User;

import java.util.List;

public interface TrailerDAO {
    List<Trailer> getTrailers();

    void saveTrailer(Trailer trailer);

    Trailer getTrailer(int id);

    void deleteTrailer(int id);

    void updateTrailer(Trailer trailer);
    public List<Trailer> getTrailerByCompany(Company company);
}