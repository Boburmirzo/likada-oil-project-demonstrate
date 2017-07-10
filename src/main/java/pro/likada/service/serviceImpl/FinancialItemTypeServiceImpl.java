package pro.likada.service.serviceImpl;

import pro.likada.dao.FinancialItemTypeDAO;
import pro.likada.model.FinancialItemType;
import pro.likada.service.FinancialItemTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Created by bumur on 27.02.2017.
 */
@Named("financialItemTypeService")
@Transactional
public class FinancialItemTypeServiceImpl implements FinancialItemTypeService {

    @Inject
    private FinancialItemTypeDAO financialItemTypeDAO;

    @Override
    public FinancialItemType findById(long id) {
        return financialItemTypeDAO.findById(id);
    }
}
