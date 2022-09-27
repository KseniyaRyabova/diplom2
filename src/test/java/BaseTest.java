import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import java.util.ArrayList;

public class BaseTest {
    public static RequestSpecification specification;
    static ArrayList<String> usersTokens = new ArrayList<>();

    public static final String registerUrl = "/api/auth/register";
    public static final String authUrl = "api/auth/user";
    public static final String ingredientUrl = "/api/ingredients";
    public static final String ordersUrl = "/api/orders";
    public static final String loginUrl = "/api/auth/login";

    @BeforeClass
    public static void getUp() {
        //инициализация общих параметров запросов
        specification = RestAssured.given();
        specification.baseUri("https://stellarburgers.nomoreparties.site");
        specification.header("Content-type", "application/json");
    }


}
