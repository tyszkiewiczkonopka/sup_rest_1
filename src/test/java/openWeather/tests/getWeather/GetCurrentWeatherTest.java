package openWeather.tests.getWeather;

import lombok.extern.slf4j.Slf4j;
import openWeather.model.config.CityConfiguration;
import openWeather.model.openWeather.City;
import openWeather.services.EndpointProvider;
import openWeather.services.YmlReader;
import openWeather.tests.BaseAPITest;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@Execution(ExecutionMode.CONCURRENT)
public class GetCurrentWeatherTest extends BaseAPITest {

    private static List<CityConfiguration> getCities() throws IOException {
        return new YmlReader().getCitiesFromConfig();
    }

    @ParameterizedTest
    @MethodSource(value = "getCities")
    public void should_getCurrentWeatherByCityData(CityConfiguration cityConfig) {
        City city = getCity(cityConfig.getName(), cityConfig.getUsStateCode(), cityConfig.getCountryCodeIso3166());
        Float cityLatitude = city.getLat();
        Float cityLongitude = city.getLon();

        given()
                .param("lat", cityLatitude)
                .param("lon", cityLongitude)
                .param("units", "metric")

                .when()
                .get(EndpointProvider.CURRENT_WEATHER_ENDPOINT)

                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(city.getName()));
    }

    /**
     * Fetches lat and lon based on city data. It was required to use non-deprecated API and still use city name as
     * test parameter.
     *
     * @see <a href="https://openweathermap.org/current#builtin">Built-in geocoding</a>
     */
    private City getCity(String cityName, String usStateCode, String countryCodeIso3166) {
        return given()
                .param("q", cityName + "," + usStateCode + "," + countryCodeIso3166)
                .param("limit", "1")

                .when()
                .get(EndpointProvider.CITY_INFORMATION_ENDPOINT)
                .jsonPath().getList(".", City.class).get(0);
    }
}
