package commons;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.specification.RequestSpecification;
import utils.ConfigHelper;

public class BaseTest {

    private final ConfigHelper configHelper = ConfigHelper.getInstance();

    public RequestSpecification initBaseRequest(String mainUrl, String apiVersion) {
        String apiVer = (System.getProperty("api.version") != null) ? System.getProperty("api.version") : ((apiVersion != null) ? apiVersion : "api_version_2");
        RestAssured.baseURI = mainUrl + configHelper.getProperty(apiVersion);
        RequestSpecification request = RestAssured.given().config(
                RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        request.header("Content-Type", configHelper.getProperty("content_type"));
        request.header("Accept-Charset", configHelper.getProperty("charset"));

        return request;
    }
}
