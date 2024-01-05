package openWeather.model.config;

import lombok.Getter;

import java.util.List;

@Getter
public class Config {

    private List<CityConfiguration> cities;

    public Config() {
    }

}
