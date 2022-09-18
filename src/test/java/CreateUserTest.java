import dto.User;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CreateUserTest extends BaseTest {

    @Test
    public void createUserWithoutAttr() {
        User user = new User("", "");
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
