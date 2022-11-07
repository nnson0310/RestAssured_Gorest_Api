package gorest;

import api_endpoints.UserEndPoints;
import api_models.requests.CreateUserRequest;
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
public class Update_Users extends BaseTest {

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
    public void TC_01_Update_User_Success() {
        String updateEmail = dataFaker.getEmail();
        String updateName = dataFaker.getFullName();
        String updateGender = dataFaker.getGender();
        String updateStatus = dataFaker.getStatus();

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId, updateEmail, updateName, updateGender, updateStatus);
        Response response = new UserEndPoints().updateUser(request, updateUserRequest);

        System.out.println(response.body().asString());

        CreateUserResponse createUserResponse = new Gson().fromJson(response.getBody().asString(), CreateUserResponse.class);
        assertThat(createUserResponse.getEmail(), equalTo(updateEmail));
        assertThat(createUserResponse.getName(), equalTo(updateName));
        assertThat(createUserResponse.getGender(), equalTo(updateGender));
        assertThat(createUserResponse.getStatus(), equalTo(updateStatus));
        assertThat(createUserResponse.getId(), equalTo(userId));
    }
}
