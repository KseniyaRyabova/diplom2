package dto;

import java.util.ArrayList;

public class CreateOrderRequest {
    private ArrayList<Ingredient> ingredients;

    public CreateOrderRequest(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
