import dto.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest {

    public static String generatedString = RandomStringUtils.random(20, true, true);
    public static String email = generatedString.concat("a@gmail.com");

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
    public void singInWithValidData() {
        User user = new User(email, password);
        userClient.authUser(user)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.name", equalTo("ksyusha"));
    }

    @Test
    public void singInWithNotValidData() {
        User user = new User(email, password.concat("1"));
        userClient.authUser(user)
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
