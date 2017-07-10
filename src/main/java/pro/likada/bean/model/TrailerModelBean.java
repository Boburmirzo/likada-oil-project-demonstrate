package pro.likada.bean.model;

import pro.likada.model.Trailer;
import pro.likada.service.TrailerService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by abuca on 07.03.17.
 */
@Named
@SessionScoped
public class TrailerModelBean implements Serializable{
    @Inject
    private TrailerService trailerService;

    private String sortingField;

    @PostConstruct
    public void init(){
        this.sortingField = "govNumber";
    }


    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    private Trailer selectedTrailer;

    public Trailer getSelectedTrailer() {
        return selectedTrailer;
    }

    public void setSelectedTrailer(Trailer selectedTrailer) {
        this.selectedTrailer = selectedTrailer;
    }
}
