package org.example.spoonaccular;

import org.example.EquipmentItem;
import org.example.EquipmentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class HomeWorkFourTest extends SpoonaccularTest {
    @Test
    void testSearchIngredients() throws Exception {

        String actually = given()
                .param("ingredients", "apples")
                .param("number", 10)
                .expect()
                .when()
                .get("recipes/findByIngredients")
                .body()
                .prettyPrint();
        String expected = getResource("expected.json");
        assertJson(expected, actually);
        }


    @Test
    void testTasteRecipeById() {
        given()
                .pathParam("id", 65342)
                .expect()
                .body("sweetness", is(100.0F))
                .body("saltiness", is(10.94F))
                .body("sourness", is(46.54F))
                .body("bitterness", is(12.64F))
                .body("savoriness", is(9.8F))
                .body("fattiness", is(49.81F))
                .body("spiciness", is(0.0F))
                .when()
                .get("recipes/{id}/tasteWidget.json");
    }


    @Test
    void testParseIngredients() throws IOException {

        String actually;
        actually = given()
                .param("ingredients", "1 cup green tea")
                .param("servings", 1)
                .body("green tea")
                .expect()
                .when()
                .get("recipes/parseIngredients")
                .body()
                .prettyPrint();

    }
    @Test
    void testEquipmentById() {

        EquipmentItem target = new EquipmentItem("pie-pan.png", "pie form");

        EquipmentResponse response = given()
                .pathParam("id", 1003464)
                .expect()
                .when()
                .get("recipes/{id}/equipmentWidget.json")
                .as(EquipmentResponse.class);

        response.getEquipment()
                .stream()
                .filter(item -> item.getName().equals("pie form"))
                .peek(item -> Assertions.assertEquals(target, item))
                .findAny()
                .orElseThrow();
    }
}
