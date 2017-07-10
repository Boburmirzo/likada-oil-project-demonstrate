package pro.likada.service.serviceImpl;

import pro.likada.dao.BankAccountDAO;
import pro.likada.model.BankAccount;
import pro.likada.service.BankAccountService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 12/22/2016.
 */
@Named("bankAccountService")
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    @Inject
    private BankAccountDAO bankAccountDAO;

    @Override
    public BankAccount findById(long id) {
        return bankAccountDAO.findById(id);
    }

    @Override
    public List<BankAccount> findByName(String name) {
        return bankAccountDAO.findByName(name);
    }

    @Override
    public List<BankAccount> findByBankNumber(String bankNumber) {
        return bankAccountDAO.findByBankNumber(bankNumber);
    }

    @Override
    public List<BankAccount> findByBankAccountNumber(String bankAccountNumber) {
        return bankAccountDAO.findByBankAccountNumber(bankAccountNumber);
    }

    @Override
    public void save(BankAccount bankAccount) {
        bankAccountDAO.save(bankAccount);
    }

    @Override
    public void deleteById(long id) {
        bankAccountDAO.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        bankAccountDAO.deleteByName(name);
    }

    @Override
    public void deleteByBankNumber(String bankNumber) {
        bankAccountDAO.deleteByBankNumber(bankNumber);
    }

    @Override
    public void deleteByBankAccountNumber(String bankAccountNumber) {
        bankAccountDAO.deleteByBankAccountNumber(bankAccountNumber);
    }

    @Override
    public List<BankAccount> findAllBankAccounts(String searchString) {
        return bankAccountDAO.findAllBankAccounts(searchString);
    }
}
