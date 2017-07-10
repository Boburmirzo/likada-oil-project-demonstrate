package pro.likada.rest.server;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import pro.likada.dao.*;
import pro.likada.dao.daoImpl.*;

/**
 * Created by bumur on 03.04.2017.
 */
public class ApplicationBinder extends AbstractBinder{
    @Override
    protected void configure() {

        bind(DriveDAOImpl.class).to(DriveDAO.class);
        bind(DriveStateDAOImpl.class).to(DriveStateDAO.class);
        bind(DriveStateTypeDAOImpl.class).to(DriveStateTypeDAO.class);
        bind(RestAuthenticationDAOImpl.class).to(RestAuthenticationDAO.class);
        bind(DocumentDAOImpl.class).to(DocumentDAO.class);
        bind(DocumentTypeDAOImpl.class).to(DocumentTypeDAO.class);
    }
}
