package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ContractorOrganizationTypeDAO;
import pro.likada.model.ContractorOrganizationType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 04.01.2017.
 */
@SuppressWarnings("unchecked")
@Named("contractorOrganizationTypeDAO")
@Transactional
public class ContractorOrganizationTypeDAOImpl implements ContractorOrganizationTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractorOrganizationTypeDAOImpl.class);

    @Override
    public List<ContractorOrganizationType> getAllContractorOrganizationTypes() {
        LOGGER.info("Get All ContractorOrganizationTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ContractorOrganizationType> contractors = (List<ContractorOrganizationType>) session.createCriteria(ContractorOrganizationType.class).list();
        session.close();
        return contractors;
    }
}
