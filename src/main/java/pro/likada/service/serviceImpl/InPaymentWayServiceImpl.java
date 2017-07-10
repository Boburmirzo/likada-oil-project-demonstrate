package pro.likada.service.serviceImpl;

import pro.likada.dao.InPaymentWayDAO;
import pro.likada.model.InPaymentWay;
import pro.likada.service.InPaymentWayService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 27.02.2017.
 */
@Named("inPaymentTypeService")
@Transactional
public class InPaymentWayServiceImpl implements InPaymentWayService {

    @Inject
    private InPaymentWayDAO inPaymentTypeDAO;

    @Override
    public InPaymentWay findById(long id) {
        return inPaymentTypeDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        inPaymentTypeDAO.deleteById(id);
    }

    @Override
    public void save(InPaymentWay inPaymentWay) {
        inPaymentTypeDAO.save(inPaymentWay);
    }

    @Override
    public List<InPaymentWay> getAllInpaymentWaysByFinancialItemId(Long id) {
        return inPaymentTypeDAO.getAllInpaymentWaysByFinancialItemId(id);
    }

    @Override
    public String getShortInforAboutInPayments(List<InPaymentWay> inPaymentWays) {
        String shortInformation="";

//        for (InPaymentWay inPaymentWay:inPaymentWays){
//            shortInformation+=inPaymentWay.ge
//        }
        return null;
    }

}
