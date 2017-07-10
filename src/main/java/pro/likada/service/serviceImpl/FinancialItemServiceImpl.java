package pro.likada.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.FinancialItemDAO;
import pro.likada.model.Contractor;
import pro.likada.model.Customer;
import pro.likada.model.FinancialItem;
import pro.likada.model.InPaymentWay;
import pro.likada.service.FinancialItemService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 27.02.2017.
 */
@Named("financialItemService")
@Transactional
public class FinancialItemServiceImpl implements FinancialItemService, Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialItemServiceImpl.class);


    @Inject
    private FinancialItemDAO financialItemDAO;

    @Override
    public FinancialItem findById(long id) {
        return financialItemDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        financialItemDAO.deleteById(id);
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsForSearchString(Date dateFrom, Date dateTo,Contractor contractorFrom, Contractor contractorTo) {
        return financialItemDAO.findAllFinancialItemsForSearchString(dateFrom,dateTo,contractorFrom,contractorTo);
    }

    @Override
    public void save(FinancialItem financialItem) {
        financialItemDAO.save(financialItem);
    }

    @Override
    public Double calculateSum(List<InPaymentWay> inPaymentWays) {
        double rv = 0.0;
        if (inPaymentWays != null) {
            if (!inPaymentWays.isEmpty()) {
                for (InPaymentWay inPaymentWay:inPaymentWays) {
                    if (inPaymentWay != null) {
                        if (inPaymentWay.getSum() != null) {
                            rv += inPaymentWay.getSum();
                        }
                    }
                }
            }
        }
        return rv;
    }

    @Override
    public int differenceBetweenOverallSumAndSumOfInPaymentWays(Double sum, Double overallSum) {
        if(sum==null) {
            return 0;
        }
        if(overallSum==null){
            return 1;
        }

        if (sum.equals(overallSum)) {
            return 0;
        }
        return 1;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorFromForSearchString(Date dateFrom, Date dateTo,String search, Long contractorFromId) {
        return financialItemDAO.findAllFinancialItemsOfContractorFromForSearchString(dateFrom,dateTo,search,contractorFromId);
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, List<Long> contractorIds) {
        return financialItemDAO.findAllFinancialItemsOfContractorSourceToContractorDestination(dateFrom, dateTo, contractorFrom, contractorTo,
                contractorIds);
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, Contractor contractor) {
        return financialItemDAO.findAllFinancialItemsOfContractorSourceToContractorDestination(dateFrom, dateTo, contractorFrom, contractorTo,
                contractor);
    }

    @Override
    public Double findDebitOrCredit(Date dateFrom, Date dateTo, Long contractorFromId, Long contractorToId) {
        double overallSum=0;
        List<FinancialItem> financialItems = financialItemDAO.findAllFinancialItemsOfContractorSourceToContractorDestination(dateFrom, dateTo, contractorFromId, contractorToId);
        for (FinancialItem financialItem:financialItems){
            if(financialItem.isActive()) {
                if (financialItem.getSum() != null)
                    overallSum += financialItem.getSum().doubleValue();
            }
        }
        return overallSum;
    }

    @Override
    public Double findDebitOrCreditWhenContractorsOfCustomer(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, Customer customer) {
        double overallSum=0;
        List<Long> contractorsIdsOfCustomer = new ArrayList<>();
            for(Contractor contractor:customer.getContractorsList()){
            contractorsIdsOfCustomer.add(contractor.getId());
        }

        List<FinancialItem> financialItems=financialItemDAO.findAllFinancialItemsOfContractorSourceToContractorDestination(dateFrom, dateTo, contractorFrom, contractorTo,
                contractorsIdsOfCustomer);
        for (FinancialItem financialItem:financialItems){
            if(financialItem.isActive()) {
                if (financialItem.getSum() != null)
                    overallSum += financialItem.getSum().doubleValue();
            }
        }
        return overallSum;
    }

    @Override
    public Double findDebitOrCreditWhenChoosenContractor(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, Contractor contractor) {
        double overallSum=0;
        List<FinancialItem> financialItems=financialItemDAO.findAllFinancialItemsOfContractorSourceToContractorDestination(dateFrom, dateTo, contractorFrom, contractorTo,
                contractor);
        for (FinancialItem financialItem:financialItems){
            if(financialItem.isActive()) {
                if (financialItem.getSum() != null)
                    overallSum += financialItem.getSum().doubleValue();
            }
        }
        return overallSum;
    }

    @Override
    public Double sum(Double debit, Double credit) {
        return debit-credit;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorsOfCustomers(Date dateFrom, Date dateTo,List<Contractor> contractorIds,Contractor contractorTo) {
        List<Long> contractorsIdsOfCustomer = new ArrayList<>();
        for(Contractor contractor:contractorIds){
            contractorsIdsOfCustomer.add(contractor.getId());
        }
        return financialItemDAO.findAllFinancialItemsOfContractorsOfCustomers(dateFrom,dateTo,contractorsIdsOfCustomer,contractorTo);
    }
}
