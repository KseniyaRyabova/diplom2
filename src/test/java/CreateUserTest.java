import dto.CreateAndAuthUserResponse;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CreateUserTest extends BaseTest {

    public static final String email = ((int)(Math.random()*10000))+"troyan1034953@gmail.com" + (int)(Math.random()*10000);
    public static final String password = "12345";
    public static final String name = "ksyusha";
    public static String tokenUser1;

    @BeforeClass
    public static void initData() {
        //создание тестового юзера
        User user = new User(email, password, name);
        CreateAndAuthUserResponse responseUser1 =
                given().spec(specification)
                        .body(user)
                        .when()
                        .post("/api/auth/register")
                        .body().as(CreateAndAuthUserResponse.class);
        tokenUser1 = responseUser1.getAccessToken();
    }

    @AfterClass
    public static void deleteUser() {
        given().spec(specification)
                .header("Authorization", tokenUser1)
                .when()
                .delete("api/auth/user")
                .then()
                .statusCode(202);
    }

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
