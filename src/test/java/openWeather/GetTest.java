package openWeather;

import lombok.extern.slf4j.Slf4j;
import openWeather.model.openWeather.City;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@Slf4j
public class GetTest {
    String appid = "e8061dec2ecc4f5b6e17bcae74b655b8";
    String baseUri = "https://api.openweathermap.org";
    String currentWeatherEndpoint = "/data/2.5/weather";

    @Test
    public void getLondon() {

        City london = getCity("London", "", "GB");

        Float londonLatitude = london.getLat();
        Float londonLongitude = london.getLon();

        given()
                .baseUri(baseUri)
                .param("lat", londonLatitude)
                .param("lon", londonLongitude)
                .param("appid", appid)
                .param("units", "metric")

                .when()
                .get(currentWeatherEndpoint)

                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(london.getName()));
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

