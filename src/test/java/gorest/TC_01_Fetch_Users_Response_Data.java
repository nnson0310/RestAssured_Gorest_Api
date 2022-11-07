package gorest;

import api_endpoints.UserEndPoints;
import api_models.responses.SingleUserResponse;
import api_models.responses.UsersResponse;
import com.google.gson.Gson;
import commons.BaseTest;
import commons.GlobalConstants;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TC_01_Fetch_Users_Response_Data extends BaseTest {

    private RequestSpecification request;
    public static final String name = "Gandharva Dutta";
    public static final String email = "dutta_gandharva@herman.com";
    public static final String gender = "male";
    public static final String status = "inactive";
    public static final int id = 3339;

    @BeforeClass
    @Parameters({"api_version"})
    public void setUp(String apiVersion) {
        request = super.initBaseRequest(GlobalConstants.GORES_MAIN_API_URL, apiVersion);
    }

    @Test
    public void TC_01_Fetch_All_User() {
        Response response = new UserEndPoints().fetchAllUsers(request);
        UsersResponse users = new Gson().fromJson(response.getBody().asString(), UsersResponse.class);

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(users.getCode(), equalTo(200));
        assertThat(users.getData().size(), equalTo(10));
    }

    @Test
    public void TC_02_Fetch_User_By_Query_String() {
        Response response = new UserEndPoints().fetchUserByQueryString(request, "name", name);
        UsersResponse users = new Gson().fromJson(response.getBody().asString(), UsersResponse.class);

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(users.getCode(), equalTo(200));

        assertThat(users.getData().get(0).getEmail(), equalTo(email));
        assertThat(users.getData().get(0).getGender(), equalTo(gender));
        assertThat(users.getData().get(0).getStatus(), equalTo(status));
    }

    @Test
    public void TC_03_Fetch_User_By_Path_Param() {
        Response response = new UserEndPoints().fetchUserByPathParam(request, id);
        SingleUserResponse singleUser = new Gson().fromJson(response.getBody().asString(), SingleUserResponse.class);

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(singleUser.getCode(), equalTo(200));
        assertThat(singleUser.getMeta(), is(nullValue()));

        assertThat(singleUser.getData().getEmail(), equalTo(email));
        assertThat(singleUser.getData().getGender(), equalTo(gender));
        assertThat(singleUser.getData().getStatus(), equalTo(status));
    }
}
