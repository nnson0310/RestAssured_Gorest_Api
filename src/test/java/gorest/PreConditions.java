package gorest;

import api_endpoints.UserEndPoints;
import api_models.requests.CreateUserRequest;
import api_models.responses.CreateUserResponse;
import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PreConditions {
    public static int createNewUser(RequestSpecification request, String email, String name, String gender, String status) {
        CreateUserRequest createRequest = new CreateUserRequest(email, name, gender, status);
        Response response = new UserEndPoints().createUser(request, createRequest);

        System.out.println("============================Create New User=====================================");

        System.out.println(response.body().asString());

        System.out.println("=================================================================");

        CreateUserResponse createUserResponse = new Gson().fromJson(response.getBody().asString(), CreateUserResponse.class);
        assertThat(createUserResponse.getEmail(), equalTo(email));
        assertThat(createUserResponse.getName(), equalTo(name));
        assertThat(createUserResponse.getGender(), equalTo(gender));
        assertThat(createUserResponse.getStatus(), equalTo(status));
        assertThat(createUserResponse.getId(), notNullValue());

        return createUserResponse.getId();
    }
}
