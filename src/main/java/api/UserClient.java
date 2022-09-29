package api;

import dto.CreateAndAuthUserResponse;
import dto.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserClient {
    public static final String registerUrl = "/api/auth/register";
    public static final String authUrl = "api/auth/user";
    private final RequestSpecification specification;

    Response response;

    public UserClient(RequestSpecification specification) {
        this.specification = specification;
    }

    public String createUser(String email, String password, String name) {
        User user = new User(email, password, name);
        CreateAndAuthUserResponse response =
                given().spec(specification)
                        .body(user)
                        .when()
                        .post(registerUrl)
                        .body().as(CreateAndAuthUserResponse.class);
        return response.getAccessToken();

    }

    public void deleteCurrentUser(String token) {
        given().spec(specification)
                .header("Authorization", token)
                .when()
                .delete(authUrl)
                .then()
                .statusCode(202);
    }

    public Response changeUserData(User user, String token) {
        response = given().spec(specification)
                .body(user)
                .header("Authorization", token)
                .when()
                .patch(authUrl);
        return response;
    }
}

