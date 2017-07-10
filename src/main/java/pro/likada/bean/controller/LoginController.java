package pro.likada.bean.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.model.ContractorModelBean;
import pro.likada.bean.model.CustomerModelBean;
import pro.likada.model.Contractor;
import pro.likada.model.Customer;
import pro.likada.model.RoleEnum;
import pro.likada.model.User;
import pro.likada.service.UserService;
import pro.likada.util.AccessTypeEnum;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Created by Yusupov on 1/18/2017.
 */
@Named
@SessionScoped
public class LoginController implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private String username;
    private String password;
    private Boolean rememberMe;

    private User currentUser;

    @Inject
    private CustomerModelBean customerModelBean;
    @Inject
    private ContractorModelBean contractorModelBean;

    @Inject
    private UserService userService;

    public LoginController() {
    }

    /**
     * Try and authenticate the user
     */
    public void doLogin() {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            LOGGER.info("Logging in using {}", token);
            subject.login(token);
            if (currentUser==null)
                currentUser = userService.findByUsername((String)SecurityUtils.getSubject().getPrincipal());
            FacesContext.getCurrentInstance().getExternalContext().redirect("customers.xhtml");
        }
        catch (UnknownAccountException ex) {
            facesError(resourceBundle.getString("login.noSuchUsername"),resourceBundle.getString("login.noSuchUsername"));
            LOGGER.error(ex.getMessage(), ex);
        }
        catch (IncorrectCredentialsException ex) {
            facesError(resourceBundle.getString("login.wrongPassword"),resourceBundle.getString("login.wrongPassword"));
            LOGGER.error(ex.getMessage(), ex);
        }
        catch (LockedAccountException ex) {
            facesError(resourceBundle.getString("login.accountIsLocked"),resourceBundle.getString("login.accountIsLocked"));
            LOGGER.error(ex.getMessage(), ex);
        }
        catch (AuthenticationException | IOException ex) {
            facesError(resourceBundle.getString("login.pleaseTryAgainLater"),resourceBundle.getString("login.pleaseTryAgainLater"));
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            token.clear();
        }
    }

    public void doLogout(){
        try {
            currentUser=null;
            SecurityUtils.getSubject().logout();
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public User getCurrentUser() {
        if(SecurityUtils.getSubject().isAuthenticated() && currentUser==null)
            currentUser = userService.findByUsername((String)SecurityUtils.getSubject().getPrincipal());
        return currentUser;
    }

    public boolean hasAccessTo(ModelConstantEnum model, AccessTypeEnum access){
        if(SecurityUtils.getSubject().hasRole(RoleEnum.ADMIN.getRoleType()))
            return true;
        else
            return SecurityUtils.getSubject().isPermitted(model.getModelName() + ":" + access.getAccessName());
    }

    public boolean isCustomerSubordinateOfUser(Customer customer){
        if(currentUser==null)
            currentUser = userService.findByUsername((String)SecurityUtils.getSubject().getPrincipal());
        if(customer!=null){
            for (User u: customer.getCustomerManagers())
                if(currentUser.equals(u) || currentUser.getSubordinates().contains(u))
                    return true;
        }
        return false;
    }

    public boolean isContractorSubordinateOfUser(Contractor contractor){
        if(currentUser==null)
            currentUser = userService.findByUsername((String)SecurityUtils.getSubject().getPrincipal());
        if(contractor!=null && contractor.getCustomer()!=null){
            for (User u: contractor.getCustomer().getCustomerManagers())
                if(currentUser.equals(u) || currentUser.getSubordinates().contains(u))
                    return true;
        }
        return false;
    }

    /**
     * Adds a new SEVERITY_ERROR FacesMessage for the ui
     * @param summary Short Summary for error message
     * @param details Full description of error message
     */
    private void facesError(String summary, String details) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, details));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean lembrar) {
        this.rememberMe = lembrar;
    }
}
