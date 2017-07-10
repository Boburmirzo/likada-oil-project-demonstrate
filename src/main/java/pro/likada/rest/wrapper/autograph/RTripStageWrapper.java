package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by abuca on 27.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTripStageWrapper {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Alias")
    private String alias;
    @JsonProperty("Params")
    private String[] params;
    @JsonProperty("Items")
    private RTripStageItemWrapper[] items;
    @JsonProperty("Statuses")
    private RParameterStatusWrapper[] statuses;
    @JsonProperty("Total")
    private Map<String,String > finalData;

    public List<Double> getDistances(){
        List<Double> distances = new ArrayList<>();
        int position = Arrays.asList(params).indexOf(ResourceBundle.getBundle("autograph").getString("distance_col_name"));
        if(position == -1){
            return distances;
        }
        distances = Arrays.asList(items).stream()
                .map(rTripStageItemWrapper -> rTripStageItemWrapper.getValues()[position])
                .map(s -> Double.valueOf(s))
                .collect(Collectors.toList());
        return distances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public RParameterStatusWrapper[] getStatuses() {
        return statuses;
    }

    public void setStatuses(RParameterStatusWrapper[] statuses) {
        this.statuses = statuses;
    }

    public Map<String, String> getFinalData() {
        return finalData;
    }

    public void setFinalData(Map<String, String> finalData) {
        this.finalData = finalData;
    }

    public RTripStageItemWrapper[] getItems() {
        return items;
    }

    public void setItems(RTripStageItemWrapper[] items) {
        this.items = items;
    }
}
