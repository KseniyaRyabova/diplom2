import api.OrderClient;
import api.UserClient;
import dto.CreateOrderRequest;
import dto.GetIngredientsRequest;
import dto.Ingredient;
import dto.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {
    public static ArrayList<Ingredient> ingredients = new ArrayList<>();
    public static CreateOrderRequest request;

    public static String generatedString = RandomStringUtils.random(20, true, true);
    public static String email = generatedString.concat("a@gmail.com");

    public static User user1 = new User(email, password, name);
    public static String tokenUser1;

    @BeforeClass
    public static void initData() {
        tokenUser1 = userClient.getToken(user1);
    }

    @AfterClass
    public static void deleteUser() {
        userClient.deleteCurrentUser(tokenUser1);
    }

    @Before
    public void initIngredient() {
        GetIngredientsRequest ingredientsData = orderClient.getIngredient()
                .body().as(GetIngredientsRequest.class);
        ingredients = ingredientsData.getData();
        request = new CreateOrderRequest(new ArrayList<>());
        ingredients.forEach(ingredient -> request.getIngredients().add(ingredient));
    }

    @Test
    public void createOrderWithAuthTest() {
        orderClient.createOrder(request, tokenUser1)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutAuthTest() {
        orderClient.createOrder(request, tokenUser1)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutIngredientsTest() {
        orderClient.createOrder(tokenUser1).then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void createOrderWithNotValidIngredientHashTest() {
        Ingredient ingredient = new Ingredient("1234");
        request.getIngredients().add(ingredient);
        orderClient.createOrder(request, tokenUser1)
                .then()
                .statusCode(500);
    }

}
