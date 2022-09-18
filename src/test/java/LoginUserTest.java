import dto.CreateAndAuthUserResponse;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest {

    private static final String email = "troyan2@gmail.com";
    private static final String password = "12345";
    private static final String name = "ksyusha";
    static String token;

    @BeforeClass
    public static void createUser() {
        User user = new User(email, password, name);
        given().spec(specification)
                .body(user)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @AfterClass
    public static void deleteUser() {
        User user = new User(email, password, name);
        CreateAndAuthUserResponse response =
                given().spec(specification)
                        .body(user)
                        .post("/api/auth/login")
                        .body().as(CreateAndAuthUserResponse.class);
        token = response.getAccessToken();

        given().spec(specification)
                .header("Authorization", token)
                .when()
                .delete("api/auth/user")
                .then()
                .statusCode(202);
    }

    @Test
    public void singInWithValidData() {
        User user = new User(email, password);
        given().spec(specification)
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
        .body("refreshToken", notNullValue())
        .body("user.name", equalTo("ksyusha"));
    }

}
