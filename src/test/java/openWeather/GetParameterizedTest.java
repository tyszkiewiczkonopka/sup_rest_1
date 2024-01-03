package openWeather;

import openWeather.model.openWeather.City;
import openWeather.services.YmlReader;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@Execution(ExecutionMode.CONCURRENT)
public class GetParameterizedTest {
    String appid = "e8061dec2ecc4f5b6e17bcae74b655b8";
    String baseUri = "https://api.openweathermap.org";
    String currentWeatherEndpoint = "/data/2.5/weather";

    public static List<openWeather.model.config.City> getCities() throws IOException {
        return new YmlReader().getCitiesFromConfig();
    }

    @ParameterizedTest
    @MethodSource(value = "getCities")
    public void getLondonTest(openWeather.model.config.City cityConfig) {

        City city = getCity(cityConfig.getName(), cityConfig.getUsStateCode(), cityConfig.getCountryCodeIso3166());

        Float cityLatitude = city.getLat();
        Float cityLongitude = city.getLon();

        given()
                .baseUri(baseUri)
                .param("lat", cityLatitude)
                .param("lon", cityLongitude)
                .param("appid", appid)
                .param("units", "metric")

                .when()
                .get(currentWeatherEndpoint)

                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(city.getName()));
    }


    private City getCity(String cityName, String usStateCode, String countryCodeIso3166) {
        return given()
                .baseUri(baseUri)
                .param("appid", appid)
                .param("q", cityName + "," + usStateCode + "," + countryCodeIso3166)
                .param("limit", "1")
                .when()
                .get("/geo/1.0/direct")
                .jsonPath().getList(".", City.class).get(0);
    }
}
