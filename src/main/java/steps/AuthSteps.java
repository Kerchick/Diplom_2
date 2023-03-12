package steps;

import config.Configuration;
import io.qameta.allure.Step;
import model.AuthModel;
import model.RegistrationModel;


import static io.restassured.RestAssured.given;

public class AuthSteps extends Configuration{


    @Step("Авторизация пользователем")
    public static void loginUser(String email, String password){
        AuthModel auth = new AuthModel();
        auth.authModel(email,password);
        authResponse = given().log().all()
                .spec(sendHeader)
                .body(auth)
                .when()
                .post(BaseUrl + apiAuthLogin);
    }
}
