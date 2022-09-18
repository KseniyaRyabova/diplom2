import dto.Ingredient;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import java.util.ArrayList;

public class BaseTest {
    public static RequestSpecification specification;
    static ArrayList<String> usersTokens = new ArrayList<>();

    @BeforeClass
    public static void getUp() {
        //инициализация общих параметров запросов
        specification = RestAssured.given();
        specification.baseUri("https://stellarburgers.nomoreparties.site");
        specification.header("Content-type", "application/json");
    }


}
