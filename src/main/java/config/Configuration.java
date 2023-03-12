package config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Configuration {
    public static final String BaseUrl = "https://stellarburgers.nomoreparties.site/";
    public static final String apiOrders = "api/orders";
    public static final String apiAuthRegister = "api/auth/register";
    public static final String apiAuthLogin = "api/auth/login";
    public static final String apiAuthUser = "api/auth/user";
    public static final String apiIngredients = "api/ingredients";
    public static String email;
    public static String password;
    public static String name;
    public static String token;
    public static Response registrationResponse;
    public static Response editUserResponse;
    public static Response authResponse;
    public static Response ingredientsResponse;
    public static Response orderListResponse;
    public static Response orderCreateResponse;
    public static Response deleteResponse;




    public static RequestSpecification sendHeader = given()
            .filters(new AllureRestAssured(), new ResponseLoggingFilter())
            .contentType(ContentType.JSON).accept(ContentType.JSON);
}
