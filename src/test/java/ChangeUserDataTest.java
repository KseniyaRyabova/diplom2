import dto.User;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends BaseTest {

    @Test
    public void changeNameAuthUserTest() {
        User user = new User(email, password);
        String newUserName = "newName";
        user.setName(newUserName);
        given().spec(specification)
                .body(user)
                .header("Authorization", token)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(200)
                .body("user.name", equalTo(newUserName));
    }

    @Test
    public void changeEmailAuthUserTest() {
        User user = new User(email, password);
        String newUserEmail = "new.mail@email.com";
        user.setEmail(newUserEmail);
        given().spec(specification)
                .body(user)
                .header("Authorization", token)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(200)
                .body("user.email", equalTo(newUserEmail));
    }

    @Test
    public void changeNotAuthUserTest() {
        User user = new User(email, password);
        String newUserName = "newName";
        user.setName(newUserName);
        given().spec(specification)
                .body(user)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    public void changeAuthUserExistEmailTest() {
        User user = new User(email, password);
        user.setEmail("troyan77@gmail.com");
        given().spec(specification)
                .body(user)
                .header("Authorization", token)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(403)
                .body("message", equalTo("User with such email already exists"));
    }
}
