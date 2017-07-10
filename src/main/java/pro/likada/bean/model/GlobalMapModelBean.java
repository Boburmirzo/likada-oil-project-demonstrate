package pro.likada.bean.model;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 * Created by abuca on 21.03.17.
 */
@Named
@SessionScoped
public class GlobalMapModelBean {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
