package org.example.spoonaccular;

import io.restassured.RestAssured;
import org.example.EquipmentItem;
import org.example.EquipmentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class HomeWorkThreeTest {
    private static final String API_KEY = "aca4dd311ed84a359099bcc68b437e67";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/recipes/";
    }

    @Test
    void testSearchIngredients() throws IOException {

        String actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .param("ingredients", "apples")
                .param("number", 10)
                .expect()
                .log()
                .body()
                .when()
                .get("findByIngredients")
                .body()
                .prettyPrint();
    }

    @Test
    void testTasteRecipeById() {
        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 65342)
                .expect()
                .log()
                .body()
                .body("sweetness", is(100.0F))
                .body("saltiness", is(10.94F))
                .body("sourness", is(46.54F))
                .body("bitterness", is(12.64F))
                .body("savoriness", is(9.8F))
                .body("fattiness", is(49.81F))
                .body("spiciness", is(0.0F))
                .when()
                .get("{id}/tasteWidget.json");
    }


    @Test
    void testParseIngredients() throws IOException {

        String actually;
        actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .param("ingredients", "1 cup green tea")
                .param("servings", 1)
                .body("green tea")
                .expect()
                .log()
                .body()
                .when()
                .get("parseIngredients")
                .body()
                .prettyPrint();

    }
    @Test
    void testEquipmentById() {

        EquipmentItem target = new EquipmentItem("pie-pan.png", "pie form");

        EquipmentResponse response = given()
                .param("apiKey", API_KEY)
                .pathParam("id", 1003464)
                .expect()
                .when()
                .get("{id}/equipmentWidget.json")
                .as(EquipmentResponse.class);

        response.getEquipment()
                .stream()
                .filter(item -> item.getName().equals("pie form"))
                .peek(item -> Assertions.assertEquals(target, item))
                .findAny()
                .orElseThrow();
    }
}
