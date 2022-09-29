import api.OrderClient;
import api.UserClient;
import dto.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends BaseTest {
    public static ArrayList<String> usersTokens = new ArrayList<>();

    public static UserClient userClient = new UserClient(specification);

    static String generatedString = RandomStringUtils.random(20, false, true);
    public static String emailAuth = generatedString.concat("@gmail.com");
    public static String email = generatedString.concat("0@gmail.com");

    public static String tokenUser1;
    public static String tokenUser2;

    static User user1 = new User(email, password, name);
    static User user2 = new User(emailAuth, password, name);

    @BeforeClass
    public static void initData() {
        tokenUser1 = userClient.getToken(user1);
        tokenUser2 = userClient.getToken(user2);
        usersTokens.add(tokenUser1);
        usersTokens.add(tokenUser2);
    }

    @AfterClass
    public static void deleteUser() {
        for (String token : usersTokens) {
            userClient.deleteCurrentUser(token);
        }
    }

    @Test
    public void changeNameAuthUserTest() {
        UserClient userClient = new UserClient(specification);
        User user = new User(email, password);
        String newUserName = "newName";
        user.setName(newUserName);

        String dataType = "user.name";

        userClient.changeUserData(user, tokenUser1)
                .then()
                .statusCode(200)
                .body(dataType, equalTo(newUserName));
    }

    @Test
    public void changeEmailAuthUserTest() {
        UserClient userClient = new UserClient(specification);

        User user = new User(email, password);
        String newUserEmail = "new.mail@email.com+1";
        user.setEmail(newUserEmail);

        String dataType = "user.email";

        userClient.changeUserData(user, tokenUser1)
                .then()
                .statusCode(200)
                .body(dataType, equalTo(newUserEmail));
    }

    @Test
    public void changeNotAuthUserTest() {
        UserClient userClient = new UserClient(specification);

        User user = new User(email, password);
        String newUserName = "newName";
        user.setName(newUserName);

        String message = "message";
        String messageText = "You should be authorised";

        userClient.changeUserData(user, "")
                .then()
                .statusCode(401)
                .body(message, equalTo(messageText));
    }

    @Test
    public void changeAuthUserExistEmailTest() {
        UserClient userClient = new UserClient(specification);
        User user = new User(email, password);
        user.setEmail(emailAuth);

        String message = "message";
        String messageText = "User with such email already exists";

        userClient.changeUserData(user, tokenUser1)
                .then()
                .statusCode(403)
                .body(message, equalTo(messageText));
    }
}
