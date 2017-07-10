package pro.likada.service.serviceImpl;

import pro.likada.dao.AddressRegionDAO;
import pro.likada.model.AddressRegion;
import pro.likada.service.AddressRegionService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@Named("addressRegionService")
@Transactional
public class AddressRegionServiceImpl implements AddressRegionService, Serializable {

    @Inject
    private AddressRegionDAO addressRegionDAO;

    @Override
    public List<AddressRegion> getAddressRegions() {
        return addressRegionDAO.getAddressRegions();
    }
}
