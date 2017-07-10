package pro.likada.dao;

import pro.likada.model.BankAccount;

import java.util.List;

/**
 * Created by Yusupov on 12/22/2016.
 */
public interface BankAccountDAO {

    BankAccount findById(long id);

    List<BankAccount> findByName(String name);

    List<BankAccount> findByBankNumber(String bankNumber);

    List<BankAccount> findByBankAccountNumber(String bankAccountNumber);

    void save(BankAccount bankAccount);

    void deleteById(long id);

    void deleteByName(String name);

    void deleteByBankNumber(String bankNumber);

    void deleteByBankAccountNumber(String bankAccountNumber);

    List<BankAccount> findAllBankAccounts(String searchString);

}
