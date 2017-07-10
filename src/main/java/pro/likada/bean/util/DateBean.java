package pro.likada.bean.util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Date;

/**
 * Created by abuca on 31.03.17.
 */
@Named
@RequestScoped
public class DateBean {
    public Date getNow(){
        return new Date();
    }
}
