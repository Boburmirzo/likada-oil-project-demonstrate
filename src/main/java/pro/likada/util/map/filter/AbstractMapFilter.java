package pro.likada.util.map.filter;

/**
 * Created by abuca on 21.03.17.
 */
public class AbstractMapFilter {
    private String name;

    public AbstractMapFilter() {
    }

    public AbstractMapFilter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
