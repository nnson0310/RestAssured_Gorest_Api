package api_endpoints;

import api.routes.UserRoutes;
import api_models.requests.CreateUserRequest;

import api_models.requests.UpdateUserRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

    public Response updateUser(RequestSpecification request, UpdateUserRequest updateUserRequest, int userId) {
        String bearerToken = MethodHelper.getSystemEnvironmentVariable(ConfigHelper.getInstance().getProperty("api_access_token"));
//        return request
//                .log()
//                .all()
//                .header("Authorization", "Bearer " + bearerToken)
//                .pathParam("userId", userId)
//                .body(updateUserRequest)
//                .patch(UserRoutes.getBaseUserRoute() + "/{userId}");
        RequestSpecification newRequest = RestAssured.given().accept(ContentType.MULTIPART);
        return RestAssured.given(newRequest)
                .log()
                .all()
                .header("Authorization", "Bearer " + bearerToken)
                .pathParam("userId", userId)
                .formParam("email", updateUserRequest.email)
                .formParam("name", updateUserRequest.name)
                .formParam("gender", updateUserRequest.gender)
                .formParam("status", updateUserRequest.status)
                .patch(UserRoutes.getBaseUserRoute() + "/{userId}").then().log().all().extract().response();
    }

    public Response deleteUser(RequestSpecification request, int userId) {
        String bearerToken = MethodHelper.getSystemEnvironmentVariable(ConfigHelper.getInstance().getProperty("api_access_token"));
        RequestSpecification newRequest = RestAssured.given().accept(ContentType.ANY);
        return RestAssured.given(newRequest)
                .log()
                .all()
                .header("Authorization", "Bearer " + bearerToken)
                .pathParam("userId", userId)
                .delete(UserRoutes.getBaseUserRoute() + "/{userId}").then().log().all().extract().response();
    }
}
