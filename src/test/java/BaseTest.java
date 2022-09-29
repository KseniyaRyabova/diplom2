import api.OrderClient;
import api.UserClient;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

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
    }

}
