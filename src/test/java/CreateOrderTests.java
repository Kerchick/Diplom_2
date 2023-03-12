import io.qameta.allure.junit4.DisplayName;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static config.Configuration.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.ActionWithUserSteps.*;
import static steps.AuthSteps.*;
import static steps.OrderSteps.*;

public class CreateOrderTests {
    private static String ingredient1;
    private static String ingredient2;
    private final String unCorrectHash = "945master";
    @Before
    public void creteUserData() {
        email = "one1punchman@ya.ru";
        password = "punch";
        name = "Sherlock";
        registerUser(email, password, name);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    public void createOrderWithAuth(){
        loginUser(email,password);
        token = authResponse.jsonPath().getString("accessToken");
        getIngredients();
        ingredient1 = ingredientsResponse.jsonPath().getString("data._id[1]");
        ingredient2 = ingredientsResponse.jsonPath().getString("data._id[3]");
        createOrder(List.of(ingredient1,ingredient2));
        orderCreateResponse.then().assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));

    }
    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    public void createOrderWithoutAuth(){
        getIngredients();
        ingredient1 = ingredientsResponse.jsonPath().getString("data._id[1]");
        ingredient2 = ingredientsResponse.jsonPath().getString("data._id[3]");
        createOrder(List.of(ingredient1,ingredient2));
        orderCreateResponse.then().assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void createOrderWithoutIngredient(){
        loginUser(email,password);
        token = authResponse.jsonPath().getString("accessToken");
        createOrder(List.of());
        orderCreateResponse.then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));

    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithoutHash() {
        loginUser(email,password);
        token = authResponse.jsonPath().getString("accessToken");
        createOrder(List.of(unCorrectHash));
        orderCreateResponse.then().assertThat()
                .statusCode(500);

    }

    @After
    public void deleteUserAfterTest() {
        deleteUser();
    }
}
