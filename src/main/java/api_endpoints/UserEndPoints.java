package api.end_points;

import api.routes.UserRoutes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserEndPoints {

    public Response fetchUserInfo(RequestSpecification request) {
        return request.get(UserRoutes.getFetchUserRoute());
    }
}
