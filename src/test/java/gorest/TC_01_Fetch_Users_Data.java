package gorest;

import commons.BaseTest;
import commons.GlobalConstants;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TC_01_Fetch_Users_Data extends BaseTest {

    RequestSpecification request;

    @BeforeClass
    @Parameters({"api_version"})
    public void setUp(String apiVersion) {
        request = super.createUserEndPoint(GlobalConstants.GORES_MAIN_API_URL, apiVersion);
    }

    @Test
    public void TC_01_Fetch_User() {
        Response response = request.get("/users");

        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.body().asString());
    }
}
