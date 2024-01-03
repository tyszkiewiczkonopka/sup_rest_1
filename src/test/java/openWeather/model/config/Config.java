package openWeather.model.config;

import lombok.Getter;

import java.util.List;

@Getter
public class Config {

    private List<City> cities;

    public Config() {
    }

}
