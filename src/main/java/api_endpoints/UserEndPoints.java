package api_endpoints;

import api.routes.UserRoutes;
import api_models.requests.CreateUserRequest;

import api_models.requests.UpdateUserRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ConfigHelper;
import utils.MethodHelper;

public class UserEndPoints {

    public Response fetchAllUsers(RequestSpecification request) {
        return request.get(UserRoutes.getBaseUserRoute());
    }

    public Response fetchUserByQueryString(RequestSpecification request, String queryName, String queryString) {
        return request
                .queryParam(queryName, queryString)
                .get(UserRoutes.getBaseUserRoute());
    }

    public <T> Response fetchUserByPathParam(RequestSpecification request, T pathParam) {
        return request
                .pathParam("userId", pathParam)
                .get(UserRoutes.getBaseUserRoute() + "/{userId}");
    }

    public Response createUser(RequestSpecification request, CreateUserRequest createUserRequest) {
        String bearerToken = MethodHelper.getSystemEnvironmentVariable(ConfigHelper.getInstance().getProperty("api_access_token"));
        return request
                .header("Authorization", "Bearer " + bearerToken)
                .body(createUserRequest)
//                .log()
//                .all()
                .post(UserRoutes.getBaseUserRoute());
    }

    public Response updateUser(RequestSpecification request, UpdateUserRequest updateUserRequest) {
        String bearerToken = MethodHelper.getSystemEnvironmentVariable(ConfigHelper.getInstance().getProperty("api_access_token"));
        return request
                .header("Authorization", "Bearer " + bearerToken)
                .pathParam("userId", updateUserRequest.id)
                .body(updateUserRequest)
                .put(UserRoutes.getBaseUserRoute() + "/{userId}");
    }
}
