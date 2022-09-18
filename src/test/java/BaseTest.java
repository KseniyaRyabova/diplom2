import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseTest {
    public static RequestSpecification specification;

    @BeforeClass
    public static void setUp() {
        specification = RestAssured.given();
        specification.baseUri("https://stellarburgers.nomoreparties.site");
        specification.header("Content-type", "application/json");
    }

}
