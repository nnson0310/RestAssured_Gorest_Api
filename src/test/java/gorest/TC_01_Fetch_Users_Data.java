package gorest;

import api.end_points.UserEndPoints;
import api_models.responses.Users;
import com.google.gson.Gson;
import commons.BaseTest;
import commons.GlobalConstants;
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
        request = super.initBaseRequest(GlobalConstants.GORES_MAIN_API_URL, apiVersion);
    }

    @Test
    public void TC_01_Fetch_User() {
        Response response = new UserEndPoints().fetchUserInfo(request);
        Users users = new Gson().fromJson(response.getBody().asString(), Users.class);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(users.getCode(), 200);
        System.out.println(users.getMeta().getPagination().getTotal());
        System.out.println(users.getMeta().getPagination().getPages());
        System.out.println(users.getMeta().getPagination().getLimit());
    }
}
