package pro.likada.service;

import pro.likada.model.RestAuthentication;

/**
 * Created by bumur on 10.04.2017.
 */
public interface RestAuthenticationService {

    RestAuthentication findByRestUserId(Long restUserId);

    RestAuthentication findByToken(String token);

    void save(RestAuthentication restAuthentication);
}
