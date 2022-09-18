import dto.CreateOrderRequest;
import dto.Ingredient;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {


    @Test
    public void createOrderWithAuthTest() {
        CreateOrderRequest request = new CreateOrderRequest(new ArrayList<>());
        ingredients.forEach(ingredient -> {
            request.getIngredients().add(ingredient);
        });

        given().spec(specification)
                .body(request)
                .header("Authorization", tokenUser1)
                .when()
                .post("/api/orders")
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
        request.getIngredients().forEach(ingredient -> System.out.println(ingredient.get_id()));
        System.out.println(request.getIngredients().toString());

        given().spec(specification)
                .body(request)
                .when()
                .post("/api/orders")
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
                .post("/api/orders")
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
                .post("/api/orders")
                .then()
                .statusCode(500);
    }

}
