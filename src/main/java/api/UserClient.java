package api;

import dto.CreateAndAuthUserResponse;
import dto.User;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserClient {
    public static final String registerUrl = "/api/auth/register";
    public static final String authUrl = "api/auth/user";
    private final RequestSpecification specification;
    private int statusCode;
    private Map<String, String> bodyResponse;

    String nameAttr = "user.name";
    String emailAttr = "user.email";

    public UserClient(RequestSpecification specification, int statusCode, Map<String, String> bodyResponse) {
        this.specification = specification;
        this.statusCode = statusCode;
        this.bodyResponse = bodyResponse;
    }

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

    public void changeUserData(User user, String token, String dataType) {
        bodyResponse.forEach((s, s2) -> {
            if (dataType.equals("name")) {
                s = nameAttr;
            } else if (dataType.equals("email")) {
                s2 = emailAttr;
            } else {
                given().spec(specification)
                        .body(user)
                        .header("Authorization", token)
                        .when()
                        .patch(authUrl)
                        .then()
                        .statusCode(statusCode)
                        .body(s, equalTo(s2));
            }
        });

    }
}
