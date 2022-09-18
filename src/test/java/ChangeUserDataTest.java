import dto.CreateAndAuthUserResponse;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends BaseTest {
    public static final String email = ((int)(Math.random()*10000))+"troyan101013@gmail.com" + ((int)(Math.random()*10000));
    public static final String password = "12345";
    public static final String name = "ksyusha";
    public static final String emailAuth = "troyan1005610@gmail.com" + ((int)(Math.random()*10000));
    public static String tokenUser1;
    public static String tokenUser2;
    static ArrayList<String> usersTokens = new ArrayList<>();


    @BeforeClass
    public static void initData() {
        //создание тестовых данных: пользователи
        User user = new User(email, password, name);
        CreateAndAuthUserResponse responseUser1 =
                given().spec(specification)
                        .body(user)
                        .when()
                        .post("/api/auth/register")
                        .body().as(CreateAndAuthUserResponse.class);
        tokenUser1 = responseUser1.getAccessToken();
        usersTokens.add(tokenUser1);

        User user2 = new User(emailAuth, password, name);
        CreateAndAuthUserResponse responseUser2 =
                given().spec(specification)
                        .body(user2)
                        .when()
                        .post("/api/auth/register")
                        .body().as(CreateAndAuthUserResponse.class);
        tokenUser2 = responseUser2.getAccessToken();
        usersTokens.add(tokenUser2);
    }

    @AfterClass
    public static void deleteUser() {
        for (String token : usersTokens) {
            given().spec(specification)
                    .header("Authorization", token)
                    .when()
                    .delete("api/auth/user")
                    .then()
                    .statusCode(202);
        }
    }

    @Test
    public void changeNameAuthUserTest() {
        User user = new User(email, password);
        String newUserName = "newName";
        user.setName(newUserName);
        given().spec(specification)
                .body(user)
                .header("Authorization", tokenUser1)
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
                .header("Authorization", tokenUser1)
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
        user.setEmail(emailAuth);
        given().spec(specification)
                .body(user)
                .header("Authorization", tokenUser1)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(403)
                .body("message", equalTo("User with such email already exists"));
    }
}
