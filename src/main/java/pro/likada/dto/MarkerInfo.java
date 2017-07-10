package pro.likada.dto;

/**
 * Created by abuca on 28.03.17.
 */
public class MarkerInfo {
    private String type;
    private Long id;

    public MarkerInfo() {
    }

    public MarkerInfo(String type, Long id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
