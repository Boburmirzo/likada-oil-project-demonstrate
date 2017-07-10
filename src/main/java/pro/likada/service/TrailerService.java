package pro.likada.service;

import pro.likada.model.Trailer;

import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
public interface TrailerService {
    Trailer findById(long id);

    void save(Trailer truck);

    List<Trailer> findAllTrailers(String searchString);

    void deleteById(long id);

    Long findAmountOfVehiclesConnectedWithTrailer(Trailer trailer);
}
