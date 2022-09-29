import api.UserClient;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends BaseTest {
    public static final String email = ((int) (Math.random() * 10000)) + "troyan101013@gmail.com" + ((int) (Math.random() * 10000));
    public static final String password = "12345";
    public static final String name = "ksyusha";
    public static final String emailAuth = ((int) (Math.random() * 10000)) + "troyan1005610@gmail.com";
    public static String tokenUser1;
    public static String tokenUser2;
    static ArrayList<String> usersTokens = new ArrayList<>();
    static UserClient userClient = new UserClient(specification);

    @BeforeClass
    public static void initData() {
        //создание тестовых данных: пользователи
        tokenUser1 = userClient.createUser(email, password, name);
        usersTokens.add(tokenUser1);
        tokenUser2 = userClient.createUser(emailAuth, password, name);
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
        String newUserEmail = "new.mail@email.com";
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
        User user = new User(email, password);
        user.setEmail(emailAuth);

        String message = "message";
        String messageText = "User with such email already exists";

        UserClient userClient = new UserClient(specification);
        userClient.changeUserData(user, tokenUser1)
        .then()
        .statusCode(403)
        .body(message, equalTo(messageText));
    }
}
