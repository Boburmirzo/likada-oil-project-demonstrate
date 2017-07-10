package pro.likada.bean.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.TrailerBackingBean;
import pro.likada.bean.model.TrailerModelBean;
import pro.likada.model.Trailer;
import pro.likada.service.TrailerService;
import pro.likada.util.AccessTypeEnum;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
@Named
@RequestScoped
public class TrailerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrailerController.class);

    @Inject
    private TrailerBackingBean trailerBackingBean;
    @Inject
    private TrailerModelBean trailerModelBean;
    @Inject
    private TrailerService trailerService;
    @Inject
    private LoginController loginController;

    private void refreshTrailersTable(){
        LOGGER.info("Refreshing Trailers table");
        trailerBackingBean.setTrailerList(trailerService.findAllTrailers(null));
    }

    public List<Trailer> getTrailers(){
        if(trailerBackingBean.getTrailerList()==null) {
            LOGGER.info("Unable to load trailers from bean, load from database.");
            refreshTrailersTable();
        }
        return trailerBackingBean.getTrailerList();
    }

    public void makeNewSelectedTrailer(ActionEvent actionEvent){
        LOGGER.info("Set selected trailer to new");
        if(!loginController.hasAccessTo(ModelConstantEnum.TRAILER, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot add new Trailer, User: {} has not permission",loginController.getCurrentUser());
            return;
        }
        Trailer trailer = new Trailer();
        trailerBackingBean.setSelectedTrailer(trailer);
    }

    public void selectTrailer(Trailer trailer){
        LOGGER.info("Trailer: {} is selected now", trailer);
        trailerBackingBean.setSelectedTrailer(trailer);
    }

    public void deleteSelectedTrailer(){
        LOGGER.info("Delete trailer: {}", trailerBackingBean.getSelectedTrailer());
        if(!loginController.hasAccessTo(ModelConstantEnum.TRAILER, AccessTypeEnum.DELETE)){
            LOGGER.info("Cannot delete Trailer{}, User: {} has not permission",trailerBackingBean.getSelectedTrailer(),loginController.getCurrentUser());
            return;
        }
        trailerService.deleteById(trailerBackingBean.getSelectedTrailer().getId());
        if(trailerBackingBean.getFiltredTrailerList() != null){
            if(trailerBackingBean.getFiltredTrailerList().contains(trailerBackingBean.getSelectedTrailer())){
                trailerBackingBean.getFiltredTrailerList().remove(trailerBackingBean.getSelectedTrailer());
            }
        }
        trailerBackingBean.setSelectedTrailer(null);
        refreshTrailersTable();
    }

    public void buttonActionSaveTrailer(ActionEvent actionEvent){
        LOGGER.info("Save the trailer: {}", trailerBackingBean.getSelectedTrailer());
        if(trailerBackingBean.getSelectedTrailer().getId() == null && !loginController.hasAccessTo(ModelConstantEnum.TRAILER, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot save new Trailer: {}, User: {} has not permission",trailerBackingBean.getSelectedTrailer(),loginController.getCurrentUser());
            return;
        }
        if(trailerBackingBean.getSelectedTrailer().getId() != null && !loginController.hasAccessTo(ModelConstantEnum.TRAILER, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Trailer: {}, User: {} has not permission",trailerBackingBean.getSelectedTrailer(),loginController.getCurrentUser());
            return;
        }
        trailerService.save(trailerBackingBean.getSelectedTrailer());
        refreshTrailersTable();
    }

    public Boolean isTrailerConnectedWithAnyVehicle(Trailer trailer){
        LOGGER.info("Check is Trailer connected with any vehicle, Trailer {}", trailer);
        return trailerService.findAmountOfVehiclesConnectedWithTrailer(trailer) > 0;
    }
}
