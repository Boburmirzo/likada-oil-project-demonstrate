package pro.likada.util.converter;

import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 1/25/2017.
 */
@FacesConverter("customDateTimeConverter")
public class CustomDateTimeConverter extends DateTimeConverter {

    public CustomDateTimeConverter() {
        setPattern("MMM dd, yyyy");
    }

}
