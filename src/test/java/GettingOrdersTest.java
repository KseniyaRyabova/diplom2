import dto.CreateAndAuthUserResponse;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GettingOrdersTest extends BaseTest {
    public static final String email = ((int)(Math.random()*100))+"troyan100197643@gmail.com" + (int)(Math.random()*100);
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
    public void getAuthUserOrders() {
        given().spec(specification)
                .header("Authorization", tokenUser1)
                .get("/api/orders")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    @Test
    public void getNotAuthUserOrders() {
        given().spec(specification)
                .get("/api/orders")
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
