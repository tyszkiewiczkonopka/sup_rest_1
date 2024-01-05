package openWeather.model.config;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Getter;

@Getter
public class CityConfiguration {
    public String name;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    public String usStateCode;
    public String countryCodeIso3166;

    @Override
    public String toString() {
        return name + " " + countryCodeIso3166;
    }
}
