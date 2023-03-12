package steps;

import config.Configuration;
import io.qameta.allure.Step;
import model.OrderModel;

import java.util.List;


import static io.restassured.RestAssured.given;

public class OrderSteps extends Configuration {

    @Step("Получение ингредиентов")
    public static void getIngredients(){
        ingredientsResponse = given().log().all()
                .spec(sendHeader)
                .when()
                .get(BaseUrl + apiIngredients);
    }
    @Step("Получение списка заказов")
    public static void getOrderList(){
        if (token == null) {
            orderListResponse = given().log().all()
                    .spec(sendHeader)
                    .when()
                    .get(BaseUrl + apiOrders);
        } else {
            orderListResponse = given().log().all()
                    .spec(sendHeader)
                    .header("Authorization", token)
                    .when()
                    .get(BaseUrl + apiOrders);
        }
    }
    @Step("Создание заказа")
    public static void createOrder(List<String> ingredients){
        OrderModel order = new OrderModel();
        order.OrderModel(ingredients);
        if (token == null) {
            orderCreateResponse = given().log().all()
                    .spec(sendHeader)
                    .body(order)
                    .when()
                    .post(BaseUrl + apiOrders);

        } else {
            orderCreateResponse = given().log().all()
                    .spec(sendHeader)
                    .header("Authorization", token)
                    .body(order)
                    .when()
                    .post(BaseUrl + apiOrders);


        }

    }
}
