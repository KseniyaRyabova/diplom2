import dto.CreateAndAuthUserResponse;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest {
    public static final String email = ((int)(Math.random()*10000))+"troyan1001234563@gmail.com" + (int)(Math.random()*10000);
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

    @Test
    public void singInWithNotValidData() {
        User user = new User("email@email.ur", password);
        given().spec(specification)
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

}
