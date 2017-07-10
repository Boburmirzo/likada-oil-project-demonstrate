package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abuca on 24.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class REnumDevicesWrapper {
    @JsonProperty("Items")
    private RDeviceItemWrapper[] devicesInfo;

    public RDeviceItemWrapper[] getDevicesInfo() {
        return devicesInfo;
    }

    public void setDevicesInfo(RDeviceItemWrapper[] devicesInfo) {
        this.devicesInfo = devicesInfo;
    }
}



