package openWeather.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import openWeather.model.config.City;
import openWeather.model.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class YmlReader {
    private static final String CONFIG_FILE = "src/test/resources/citiesConfig.yml";

    public List<City> getCitiesFromConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(new File(CONFIG_FILE), Config.class);
        return config.getCities();
    }
}
