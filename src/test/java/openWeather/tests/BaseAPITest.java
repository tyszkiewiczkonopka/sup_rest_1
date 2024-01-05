package openWeather.tests;

import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.requestSpecification;

public abstract class BaseAPITest {
    private static String BASE_URI = "https://api.openweathermap.org";
    private static String APP_ID = "e8061dec2ecc4f5b6e17bcae74b655b8";

    @BeforeAll
    public static void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addParam("appId", APP_ID)
                .build();
    }
}
