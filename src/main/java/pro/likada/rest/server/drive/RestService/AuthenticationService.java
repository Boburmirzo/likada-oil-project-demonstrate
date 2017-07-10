package pro.likada.rest.server.drive.RestService;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.RestAuthenticationDAO;
import pro.likada.dao.UserDAO;
import pro.likada.dao.daoImpl.UserDAOImpl;
import pro.likada.model.RestAuthentication;
import pro.likada.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by bumur on 05.04.2017.
 */
@Path("/login")
@RequestScoped
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @Inject
    private RestAuthenticationDAO restAuthenticationDAO;
    private UserDAOImpl userDAO = new UserDAOImpl();

    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private User user;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@QueryParam("username") String username,
                                     @QueryParam("password") String password) {

        if((username!=null && username.isEmpty()) || (password!=null && password.isEmpty())){
            return Response.status(Response.Status.NOT_FOUND).entity("Укажите логин и пароль: ").build();
        }
        String token="";
        if(authenticate(username, password)){
            token = issueToken();
            return Response.ok(token).build();
        } else {
            return ACCESS_DENIED;
        }

    }

    private boolean authenticate(String username, String password)  {
        user =userDAO.findByUsername(username);
        if(user!=null && user.getPasswordOriginal().equals(password))
            return true;
        return false;
    }

    private String issueToken() {
        RestAuthentication restAuthentication = restAuthenticationDAO.findByRestUserId(user.getId());
        if(restAuthentication!=null){
            return restAuthentication.getToken();
        }
        RestAuthentication newRestAuthentication = new RestAuthentication();
        newRestAuthentication.setRestUser(user);
        newRestAuthentication.setToken(RandomStringUtils.randomAlphanumeric(30).toUpperCase());
        restAuthenticationDAO.save(newRestAuthentication);
        return newRestAuthentication.getToken();
    }
}
