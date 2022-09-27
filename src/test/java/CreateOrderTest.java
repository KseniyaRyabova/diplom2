import dto.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {
    public static final String email = ((int)(Math.random()*100))+"troyan@gmail5720.com" + (int)(Math.random()*100);
    public static final String password = "12345";
    public static final String name = "ksyusha";
    public static String tokenUser1;
    public static ArrayList<Ingredient> ingredients = new ArrayList<>();

    @BeforeClass
    public static void initData() {
        //создание тестового юзера
        User user = new User(email, password, name);
        CreateAndAuthUserResponse responseUser1 =
                given().spec(specification)
                        .body(user)
                        .when()
                        .post(registerUrl)
                        .body().as(CreateAndAuthUserResponse.class);
        tokenUser1 = responseUser1.getAccessToken();
        usersTokens.add(tokenUser1);

        //получение списка всех ингрдиентов
        GetIngredientsRequest ingredientsData =
                given().spec(specification)
                        .get(ingredientUrl).body().as(GetIngredientsRequest.class);
        ingredients = ingredientsData.getData();
    }

    @AfterClass
    public static void deleteUser() {
        given().spec(specification)
                .header("Authorization", tokenUser1)
                .when()
                .delete(authUrl)
                .then()
                .statusCode(202);
    }

    @Test
    public void createOrderWithAuthTest() {
        CreateOrderRequest request = new CreateOrderRequest(new ArrayList<>());
        ingredients.forEach(ingredient -> request.getIngredients().add(ingredient));

        given().spec(specification)
                .body(request)
                .header("Authorization", tokenUser1)
                .when()
                .post(ordersUrl)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutAuthTest() {
        CreateOrderRequest request = new CreateOrderRequest(new ArrayList<>());
        ingredients.forEach(ingredient -> {
            request.getIngredients().add(ingredient);
        });
        given().spec(specification)
                .body(request)
                .when()
                .post(ordersUrl)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutIngredientsTest() {
        given().spec(specification)
                .body("")
                .header("Authorization", tokenUser1)
                .when()
                .post(ordersUrl)
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void createOrderWithNotValidIngredientHashTest() {
        CreateOrderRequest request = new CreateOrderRequest(new ArrayList<>());
        Ingredient ingredient = new Ingredient("1234");
        request.getIngredients().add(ingredient);
        given().spec(specification)
                .body(request)
                .header("Authorization", tokenUser1)
                .when()
                .post(ordersUrl)
                .then()
                .statusCode(500);
    }

}
