package pro.likada.dao;

import pro.likada.model.Contractor;
import pro.likada.model.FinancialItem;

import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 27.02.2017.
 */
public interface FinancialItemDAO {

    FinancialItem findById(long id);

    void deleteById(Long id);

    void save(FinancialItem financialItem);

    List<FinancialItem> getAllFinancialItems();

    List<FinancialItem> findAllFinancialItemsForSearchString(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo);

    List<FinancialItem> findAllFinancialItemsOfContractorFromForSearchString(Date dateFrom, Date dateTo,String search, Long contractorFromId);

    List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo,Long contractorFromId, Long contractorToId);

    List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo,Contractor contractorFrom, Contractor contractorTo,List<Long> contractorIds);

    List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo,Contractor contractorFrom, Contractor contractorTo, Contractor choosenContractor);

    List<FinancialItem> findAllFinancialItemsOfContractorsOfCustomers(Date dateFrom, Date dateTo,List<Long> contractorIds,Contractor contractorTo);

}
