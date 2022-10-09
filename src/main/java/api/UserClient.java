package api;

import dto.CreateAndAuthUserResponse;
import dto.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static api.Api.*;
import static io.restassured.RestAssured.given;

public class UserClient {

    RequestSpecification specification;

    public UserClient(RequestSpecification specification) {
        this.specification = specification;
    }

    public String getToken(User user) {
        CreateAndAuthUserResponse response =
                given().spec(specification)
                        .body(user)
                        .when()
                        .post(REGISTER_USER.path)
                        .body().as(CreateAndAuthUserResponse.class);
        return response.getAccessToken();
    }

    public Response createUser(User user) {
        return given().spec(specification)
                .body(user)
                .when()
                .post(REGISTER_USER.path);
    }

    public void deleteCurrentUser(String token) {
        given().spec(specification)
                .header("Authorization", token)
                .when()
                .delete(USER_DATA.path)
                .then()
                .statusCode(202);
    }

    public Response changeUserData(User user, String token) {
        return given().spec(specification)
                .body(user)
                .header("Authorization", token)
                .when()
                .patch(USER_DATA.path);
    }

    public Response authUser(User user) {
        return given().spec(specification)
                .body(user)
                .when()
                .post(LOGIN.path);
    }
}

