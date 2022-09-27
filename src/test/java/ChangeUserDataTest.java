import api.UserClient;
import dto.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

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
        User user = new User(email, password);
        String newUserName = "newName";
        user.setName(newUserName);
        String dataType = "name";
        Map<String, String> bodyResponse = Map.of(dataType, newUserName);
        UserClient userClient = new UserClient(specification, 200, bodyResponse);
        userClient.changeUserData(user, tokenUser1, dataType);
    }

    @Test
    public void changeEmailAuthUserTest() {
        User user = new User(email, password);
        String newUserEmail = "new.mail@email.com";
        user.setEmail(newUserEmail);

        String dataType = "email";
        Map<String, String> bodyResponse = Map.of(dataType, newUserEmail);

        UserClient userClient = new UserClient(specification, 200, bodyResponse);
        userClient.changeUserData(user, tokenUser1, dataType);
    }

    @Test
    public void changeNotAuthUserTest() {
        User user = new User(email, password);
        String newUserName = "newName";
        user.setName(newUserName);

        String dataType = "name";
        String attr = "message";
        String attrValue = "You should be authorised";

        Map<String, String> bodyResponse = Map.of(attr, attrValue);
        UserClient userClient = new UserClient(specification, 401, bodyResponse);
        userClient.changeUserData(user, "", dataType);
    }

    @Test
    public void changeAuthUserExistEmailTest() {
        User user = new User(email, password);
        user.setEmail(emailAuth);

        String dataType = "email";
        String attr = "message";
        String attrValue = "User with such email already exists";

        Map<String, String> bodyResponse = Map.of(attr, attrValue);
        UserClient userClient = new UserClient(specification, 403, bodyResponse);
        userClient.changeUserData(user, tokenUser1, dataType);
    }
}
