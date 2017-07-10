package pro.likada.dao;

import pro.likada.model.InPaymentWay;

import java.util.List;

/**
 * Created by bumur on 27.02.2017.
 */
public interface InPaymentWayDAO {

    InPaymentWay findById(long id);

    void deleteById(Long id);

    void save(InPaymentWay inPaymentWay);

    List<InPaymentWay> getAllInpaymentWaysByFinancialItemId(Long id);
}
