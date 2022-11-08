package gorest;

import api_endpoints.UserEndPoints;
import api_models.requests.UpdateUserRequest;
import api_models.responses.CreateUserResponse;
import com.google.gson.Gson;
import commons.BaseTest;
import commons.GlobalConstants;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.DataFaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SuppressWarnings("ALL")
public class Delete_User extends BaseTest {

    private RequestSpecification request;

    private DataFaker dataFaker = DataFaker.getInstance();
    private String email = dataFaker.getEmail();
    private String name = dataFaker.getFullName();
    private String gender = dataFaker.getGender();
    private String status = dataFaker.getStatus();
    private int userId;

    @BeforeMethod
    @Parameters({"api_version"})
    public void setUp(String apiVersion) {
        request = super.initBaseRequest(GlobalConstants.GORES_MAIN_API_URL, apiVersion);
        userId = PreConditions.createNewUser(request, email, name, gender, status);
    }

    @Test
    public void TC_01_Delete_User_Success() {
        Response response = new UserEndPoints().deleteUser(request, userId);

        assertThat(response.getStatusCode(), equalTo("204"));
    }
}
