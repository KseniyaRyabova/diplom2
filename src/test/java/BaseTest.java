import api.OrderClient;
import api.UserClient;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.ArrayList;

public class BaseTest {
    public static RequestSpecification specification;

    public static UserClient userClient;
    public static OrderClient orderClient;


    public static String password = "12345";
    public static String name = "ksyusha";

    @BeforeClass
    public static void getUp() {
        //инициализация общих параметров запросов

        specification = RestAssured.given();
        specification.baseUri("https://stellarburgers.nomoreparties.site");
        specification.header("Content-type", "application/json");
        userClient = new UserClient(specification);
        orderClient = new OrderClient(specification);

//
//        tokenUser1 = null;
//        tokenUser2 = null;
//
//        //создание тестовых данных: пользователи
//        try {
//            while (tokenUser1 == null) {
//                tokenUser1 = userClient.getToken(user1);
//            }
//            while (tokenUser2 == null) {
//                tokenUser2 = userClient.getToken(user2);
//            }
//        } catch (NullPointerException ex) {
//            ex.getLocalizedMessage();
//        }
//        usersTokens.add(tokenUser1);
//        usersTokens.add(tokenUser2);
//
//        System.out.println(tokenUser1);
//        System.out.println(tokenUser2);
    }

//    @AfterClass
//    public static void deleteUser() {
//        for (String token : usersTokens) {
//            if (!token.isEmpty())
//            userClient.deleteCurrentUser(token);
//        }
//    }


}
