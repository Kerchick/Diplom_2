package steps;

import config.Configuration;
import io.qameta.allure.Step;
import model.RegistrationModel;



import static io.restassured.RestAssured.given;

public class ActionWithUserSteps extends Configuration {

    @Step("Регистрация пользователя")
    public static void registerUser(String email, String password, String name){
        RegistrationModel registration = new RegistrationModel();
        registration.RegistrationModel(email, password, name);
        registrationResponse = given().log().all()
                .spec(sendHeader)
                .body(registration)
                .when()
                .post(BaseUrl+ apiAuthRegister);
    }

    @Step("Редактирование пользователя")
    public static void editUser(String email, String password, String name){
        RegistrationModel registration = new RegistrationModel();
        registration.RegistrationModel(email, password, name);
        if(token == null) {
            editUserResponse = given().log().all()
                    .spec(sendHeader)
                    .body(registration)
                    .when()
                    .patch(BaseUrl + apiAuthUser);
        } else {
            editUserResponse = given().log().all()
                    .spec(sendHeader)
                    .header("Authorization", token)
                    .body(registration)
                    .when()
                    .patch(BaseUrl + apiAuthUser);

        }
    }

    @Step("Удаление пользователя")
    public static void deleteUser(){
        if (token == null){
            deleteResponse = given().log().all()
                    .spec(sendHeader)
                    .when()
                    .delete(BaseUrl+ apiAuthUser);
        } else {
            deleteResponse = given().log().all()
                    .spec(sendHeader)
                    .header("Authorization", token)
                    .when()
                    .delete(BaseUrl+ apiAuthUser);

        }
    }
}
