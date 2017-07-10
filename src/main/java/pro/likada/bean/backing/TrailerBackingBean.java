package pro.likada.bean.backing;

import pro.likada.bean.model.TrailerModelBean;
import pro.likada.model.Trailer;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
@Named
@ViewScoped
public class TrailerBackingBean implements Serializable {
    @Inject
    private TrailerModelBean trailerModelBean;

    private Trailer selectedTrailer;
    private String searchString;
    private List<Trailer> trailerList;
    private List<Trailer> filtredTrailerList;

    @PostConstruct
    public void init(){
        if(trailerModelBean.getSelectedTrailer() != null){
            this.selectedTrailer = trailerModelBean.getSelectedTrailer();
        }
    }

    public Trailer getSelectedTrailer() {
        return selectedTrailer;
    }

    public void setSelectedTrailer(Trailer selectedTrailer) {
        this.selectedTrailer = selectedTrailer;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public List<Trailer> getFiltredTrailerList() {
        return filtredTrailerList;
    }

    public void setFiltredTrailerList(List<Trailer> filtredTrailerList) {
        this.filtredTrailerList = filtredTrailerList;
    }
}
