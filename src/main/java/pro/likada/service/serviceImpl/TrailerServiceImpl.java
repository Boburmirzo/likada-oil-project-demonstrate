package pro.likada.service.serviceImpl;

import pro.likada.dao.TrailerDAO;
import pro.likada.model.Trailer;
import pro.likada.service.TrailerService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
@Named("trailerService")
@Transactional
public class TrailerServiceImpl implements TrailerService, Serializable {
    @Inject
    TrailerDAO trailerDAO;

    @Override
    public Trailer findById(long id){
        return trailerDAO.findById(id);
    }

    @Override
    public void save(Trailer truck) {
        trailerDAO.save(truck);
    }

    @Override
    public List<Trailer> findAllTrailers(String searchString) {
        return trailerDAO.findAllTrailers(searchString);
    }

    @Override
    public Long findAmountOfVehiclesConnectedWithTrailer(Trailer trailer) {
        return trailerDAO.findAmountOfVehiclesConnectedWithTrailer(trailer);
    }

    @Override
    public void deleteById(long id){
        trailerDAO.deleteById(id);
    }
}
