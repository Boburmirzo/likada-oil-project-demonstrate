package pro.likada.service;

import pro.likada.model.Contractor;
import pro.likada.model.Customer;
import pro.likada.model.FinancialItem;
import pro.likada.model.InPaymentWay;

import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 27.02.2017.
 */
public interface FinancialItemService {

    FinancialItem findById(long id);

    void deleteById(Long id);

    List<FinancialItem> findAllFinancialItemsForSearchString(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo);

    void save(FinancialItem financialItem);

    Double calculateSum(List<InPaymentWay> inPaymentWays);

    int differenceBetweenOverallSumAndSumOfInPaymentWays(Double sum, Double overallSum);

    List<FinancialItem> findAllFinancialItemsOfContractorFromForSearchString(Date dateFrom, Date dateTo,String search, Long contractorFromId);

    List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo,Contractor contractorFrom, Contractor contractorTo,List<Long> contractorIds);

    List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, Contractor contractor);

    Double findDebitOrCredit(Date dateFrom, Date dateTo, Long contractorFromId, Long contractorToId);

    Double findDebitOrCreditWhenContractorsOfCustomer(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, Customer customer);

    Double findDebitOrCreditWhenChoosenContractor(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, Contractor contractor);

    Double sum(Double debit, Double credit);

    List<FinancialItem> findAllFinancialItemsOfContractorsOfCustomers(Date dateFrom, Date dateTo,List<Contractor> contractorIds, Contractor contractorTo);

}
