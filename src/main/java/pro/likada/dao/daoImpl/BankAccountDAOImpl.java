package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.BankAccountDAO;
import pro.likada.model.BankAccount;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 12/22/2016.
 */
@SuppressWarnings("unchecked")
@Named("bankAccountDAO")
@Transactional
public class BankAccountDAOImpl implements BankAccountDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountDAOImpl.class);

    @Override
    public BankAccount findById(long id) {
        LOGGER.info("Get Bank account with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        BankAccount bankAccount = (BankAccount) session.createCriteria(BankAccount.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return bankAccount;
    }

    @Override
    public List<BankAccount> findByName(String name) {
        LOGGER.info("Get BankAccount with name: {}", name);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BankAccount> bankAccounts = (List<BankAccount>) session.createCriteria(BankAccount.class)
                .add(Restrictions.ilike("bank_name", name, MatchMode.ANYWHERE)).list();
        session.close();
        return bankAccounts;
    }

    @Override
    public List<BankAccount> findByBankNumber(String bankNumber) {
        LOGGER.info("Get BankAccount with bankNumber: {}", bankNumber);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BankAccount> bankAccounts = (List<BankAccount>) session.createCriteria(BankAccount.class)
                .add(Restrictions.ilike("bank_number", bankNumber, MatchMode.ANYWHERE)).list();
        session.close();
        return bankAccounts;
    }

    @Override
    public List<BankAccount> findByBankAccountNumber(String bankAccountNumber) {
        LOGGER.info("Get BankAccount with bankAccountNumber: {}", bankAccountNumber);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BankAccount> bankAccounts = (List<BankAccount>) session.createCriteria(BankAccount.class)
                .add(Restrictions.ilike("bank_account_number", bankAccountNumber, MatchMode.ANYWHERE)).list();
        session.close();
        return bankAccounts;
    }

    @Override
    public void save(BankAccount bankAccount) {
        LOGGER.info("Saving a new BankAccount: {}", bankAccount);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(bankAccount);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete BankAccount with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByName(String name) {
        LOGGER.info("Delete BankAccount with name:" + name);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<BankAccount> bankAccounts = findByName(name);
        for (BankAccount bankAccount: bankAccounts)
            session.delete(bankAccount);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByBankNumber(String bankNumber) {
        LOGGER.info("Delete BankAccount with bankNumber:" + bankNumber);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<BankAccount> bankAccounts = findByName(bankNumber);
        for (BankAccount bankAccount: bankAccounts)
            session.delete(bankAccount);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByBankAccountNumber(String bankAccountNumber) {
        LOGGER.info("Delete BankAccount with bankAccountNumber:" + bankAccountNumber);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<BankAccount> bankAccounts = findByName(bankAccountNumber);
        for (BankAccount bankAccount: bankAccounts)
            session.delete(bankAccount);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<BankAccount> findAllBankAccounts(String searchString) {
        LOGGER.info("Get all BankAccounts");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(BankAccount.class, "bankAccount");
        if(searchString!=null && !searchString.isEmpty())
            criteria.add(Restrictions.or(Restrictions.ilike("bankAccount.bank_name", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("bankAccount.bank_number", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("bankAccount.bank_account_number",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("bankAccount.bik",searchString, MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("customer.bank_name"));
        criteria.addOrder(Order.asc("customer.bank_number"));
        List<BankAccount> bankAccounts = (List<BankAccount>)criteria.list();
        // No need to fetch userRoles since we are not showing them on list page. Let them lazy load.
        // Uncomment below lines for eagerly fetching of userRoles if you want.
        /*
        for(User user : users){
            Hibernate.initialize(user.getUserRoles());
        }*/
        session.close();
        return bankAccounts;
    }
}
