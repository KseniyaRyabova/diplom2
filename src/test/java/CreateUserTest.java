import dto.CreateAndAuthUserResponse;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CreateUserTest extends BaseTest {

    private static final String email = "troyan1@gmail.com";
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
    public void createUserWithoutAttr() {
        User user = new User();
        given().spec(specification)
                .body(user)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutOneAttribute() {
        User user = new User(email, name);
        given().spec(specification)
                .body(user)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createRepeatUser() {
        User user = new User(email, password, name);
        given().spec(specification)
                .body(user)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }


}
