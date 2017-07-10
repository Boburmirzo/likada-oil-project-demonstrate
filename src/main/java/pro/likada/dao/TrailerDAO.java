package pro.likada.dao;

import pro.likada.model.Trailer;

import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
public interface TrailerDAO {
    Trailer findById(long id);

    void save(Trailer trailer);

    List<Trailer> findAllTrailers(String searchString);

    void deleteById(long id);

    Long findAmountOfVehiclesConnectedWithTrailer(Trailer trailer);
}
