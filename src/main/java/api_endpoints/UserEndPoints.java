package api.end_points;

import api.routes.UserRoutes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserEndPoints {

    public Response fetchAllUsers(RequestSpecification request) {
        return request.get(UserRoutes.getFetchUserRoute());
    }

    public Response fetchUserByQueryString(RequestSpecification request, String queryName, String queryString) {
        return request.queryParam(queryName, queryString).get(UserRoutes.getFetchUserRoute());
    }

    public <T> Response fetchUserByPathParam(RequestSpecification request, T pathParam) {
        return request.pathParam("userId", pathParam).get(UserRoutes.getFetchUserRoute() + "/{userId}");
    }
}
