import dto.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest extends BaseTest {

    public static String generatedString = RandomStringUtils.random(20, true, true);
    public static String email = generatedString.concat("@gmail.com");

    public static User user1 = new User(email, password, name);
    public static String tokenUser1;

    @BeforeClass
    public static void initData() {
        tokenUser1 = userClient.getToken(user1);
    }

    @AfterClass
    public static void deleteUser() {
        userClient.deleteCurrentUser(tokenUser1);
    }

    @Test
    public void getAuthUserOrders() {
        orderClient.getOrders(tokenUser1)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    @Test
    public void getNotAuthUserOrders() {
        orderClient.getOrders("")
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
