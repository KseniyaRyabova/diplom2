import dto.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

public class GetOrdersTest extends BaseTest {

    public static String generatedString = RandomStringUtils.random(20, false, true);
    public static String email = generatedString.concat("oom@gmail.com");

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
                .body("orders", instanceOf(ArrayList.class));
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
