package pro.likada.bean.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Created by abuca on 01.04.17.
 */
@Named
@ApplicationScoped
public class AutographBean {
    public boolean isEnabled(){
        return Boolean.valueOf( ResourceBundle.getBundle("autograph").getString("enabled"));
    }
}
