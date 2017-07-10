package pro.likada.service.serviceImpl;

import pro.likada.dao.RestAuthenticationDAO;
import pro.likada.model.RestAuthentication;
import pro.likada.service.RestAuthenticationService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Created by bumur on 10.04.2017.
 */
@Named("restAuthenticationServiceImpl")
@Transactional
public class RestAuthenticationServiceImpl implements RestAuthenticationService{

    @Inject
    private RestAuthenticationDAO restAuthenticationDAO;

    @Override
    public RestAuthentication findByRestUserId(Long restUserId) {
        return restAuthenticationDAO.findByRestUserId(restUserId);
    }

    @Override
    public RestAuthentication findByToken(String token) {
        return null;
    }

    @Override
    public void save(RestAuthentication restAuthentication) {
        restAuthenticationDAO.save(restAuthentication);
    }
}
