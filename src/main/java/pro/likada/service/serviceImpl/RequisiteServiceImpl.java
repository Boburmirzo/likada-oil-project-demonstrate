package pro.likada.service.serviceImpl;

import pro.likada.dao.RequisiteDAO;
import pro.likada.model.Requisite;
import pro.likada.service.RequisiteService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 28.12.2016.
 */
@Named("requisiteService")
@Transactional
public class RequisiteServiceImpl implements RequisiteService {

    @Inject
    private RequisiteDAO requisiteDAO;

    @Override
    public Requisite findById(long id) {
        return requisiteDAO.findById(id);
    }

    @Override
    public List<Requisite> findByContractorId(long id) {
        return requisiteDAO.findByContractorId(id);
    }
}
