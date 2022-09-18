import dto.CreateAndAuthUserResponse;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseTest {
    public static RequestSpecification specification;
    public static final String email = "troyan2@gmail.com";
    public static final String password = "12345";
    public static final String name = "ksyusha";
    static String token;

    @BeforeClass
    public static void createUser() {
        specification = RestAssured.given();
        specification.baseUri("https://stellarburgers.nomoreparties.site");
        specification.header("Content-type", "application/json");

        User user = new User(email, password, name);
        CreateAndAuthUserResponse response =
                given().spec(specification)
                        .body(user)
                        .when()
                        .post("/api/auth/register")
                        .body().as(CreateAndAuthUserResponse.class);
        if (response.getAccessToken() != null) {
            token = response.getAccessToken();
        }

        User user2 = new User("troyan77@gmail.com", password, name);
        given().spec(specification)
                .body(user2)
                .when()
                .post("/api/auth/register");
    }

    @AfterClass
    public static void deleteUser() {
        given().spec(specification)
                .header("Authorization", token)
                .when()
                .delete("api/auth/user")
                .then()
                .statusCode(202);
    }

}
