import dto.CreateAndAuthUserResponse;
import dto.GettingIngredientsRequest;
import dto.Ingredient;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class BaseTest {
    public static RequestSpecification specification;
    public static ArrayList<Ingredient> ingredients = new ArrayList<>();
    public static final String email = "troyan7@gmail.com";
    public static final String password = "12345";
    public static final String name = "ksyusha";
    static ArrayList<String> usersTokens = new ArrayList<>();
    public static String tokenUser1;
    private static String tokenUser2;

    @BeforeClass
    public static void createUser() {
        //инициализация общих параметров запросов
        specification = RestAssured.given();
        specification.baseUri("https://stellarburgers.nomoreparties.site");
        specification.header("Content-type", "application/json");

        //создание тестовых данных: пользователи
        User user = new User(email, password, name);
        CreateAndAuthUserResponse responseUser1 =
                given().spec(specification)
                        .body(user)
                        .when()
                        .post("/api/auth/register")
                        .body().as(CreateAndAuthUserResponse.class);
        tokenUser1 = responseUser1.getAccessToken();
        usersTokens.add(tokenUser1);

        User user2 = new User("troyan777@gmail.com", password, name);
        CreateAndAuthUserResponse responseUser2 =
                given().spec(specification)
                        .body(user2)
                        .when()
                        .post("/api/auth/register")
                        .body().as(CreateAndAuthUserResponse.class);
        tokenUser2 = responseUser2.getAccessToken();
        usersTokens.add(tokenUser2);

        //получение списка всех ингредиентов
        GettingIngredientsRequest ingredientsData =
                given().spec(specification)
                        .get("/api/ingredients").body().as(GettingIngredientsRequest.class);
        ingredients = ingredientsData.getData();
    }

    //удаление тестовых пользователей
    @AfterClass
    public static void deleteUser() {
        for (String token : usersTokens) {
            given().spec(specification)
                    .header("Authorization", token)
                    .when()
                    .delete("api/auth/user")
                    .then()
                    .statusCode(202);
        }
    }

}
