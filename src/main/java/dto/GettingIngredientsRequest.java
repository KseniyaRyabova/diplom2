package dto;

import java.util.ArrayList;

public class GettingIngredientsRequest {
    private ArrayList<Ingredient> data;

    public GettingIngredientsRequest(ArrayList<Ingredient> data) {
        this.data = data;
    }

    public ArrayList<Ingredient> getData() {
        return data;
    }
}
