package openWeather.model.openWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    public String name;
    public String country;
    public Float lat;
    public Float lon;

    City() {

    }
}