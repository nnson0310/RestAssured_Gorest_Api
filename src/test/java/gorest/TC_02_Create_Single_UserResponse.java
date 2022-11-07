package gorest;

import api_endpoints.UserEndPoints;
import api_models.requests.CreateUserRequest;
import api_models.responses.CreateUserErrorResponse;
import api_models.responses.CreateUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("ALL")
public class TC_02_Create_Single_UserResponse extends BaseTest {

    private RequestSpecification request;

    private DataFaker dataFaker = DataFaker.getInstance();
    private String email = dataFaker.getEmail();
    private String existedEmail = email;
    private String blankFieldError = "can't be blank";
    private String existedEmailError = "has already been taken";
    private String name = dataFaker.getFullName();
    private String gender = dataFaker.getGender();
    private String status = dataFaker.getStatus();

    @BeforeMethod
    @Parameters({"api_version"})
    public void setUp(String apiVersion) {
        request = super.initBaseRequest(GlobalConstants.GORES_MAIN_API_URL, apiVersion);
    }

    @Test
    public void TC_01_Create_User_Success() {
        CreateUserRequest createRequest = new CreateUserRequest(email, name, gender, status);
        Response response = new UserEndPoints().createUser(request, createRequest);

        CreateUserResponse createUserResponse = new Gson().fromJson(response.getBody().asString(), CreateUserResponse.class);
        assertThat(createUserResponse.getEmail(), equalTo(email));
        assertThat(createUserResponse.getName(), equalTo(name));
        assertThat(createUserResponse.getGender(), equalTo(gender));
        assertThat(createUserResponse.getStatus(), equalTo(status));
        assertThat(createUserResponse.getId(), notNullValue());
    }

    @Test(dependsOnMethods = {"TC_01_Create_User_Success"})
    public void TC_02_Create_User_With_Existed_Email() throws JsonProcessingException {
        CreateUserRequest createRequest = new CreateUserRequest(existedEmail, name, gender, status);
        Response response = new UserEndPoints().createUser(request, createRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserErrorResponse[] errorResponse = objectMapper.readValue(response.body().asString(), CreateUserErrorResponse[].class);
        assertThat(errorResponse[0].getField(), equalTo("email"));
        assertThat(errorResponse[0].getMessage(), equalTo(existedEmailError));
    }

    @Test
    public void TC_03_Create_User_With_Blank_Email() throws JsonProcessingException {
        CreateUserRequest createRequest = new CreateUserRequest("", name, gender, status);
        Response response = new UserEndPoints().createUser(request, createRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserErrorResponse[] errorResponse = objectMapper.readValue(response.body().asString(), CreateUserErrorResponse[].class);
        assertThat(errorResponse[0].getField(), equalTo("email"));
        assertThat(errorResponse[0].getMessage(), equalTo(blankFieldError));
    }
}
