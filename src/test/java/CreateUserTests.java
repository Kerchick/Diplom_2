import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static config.Configuration.*;
import static steps.ActionWithUserSteps.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.AuthSteps.*;


public class CreateUserTests {

    @Before
    public void creteUserData(){
        email = "testmail123@naruta.com";
        password = "Jinchuriki";
        name = "Naruto";
    }

    @Test
    @DisplayName("Создание пользователя с валидными данными")
    public void registrationValidUserTest(){
        registerUser(email, password, name);
        registrationResponse.then().assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
        loginUser(email, password);
        token = authResponse.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Создание пользователей с одинаковыми логином ")
    public void registrationUserWithSameLoginTest(){
        registerUser(email, password, name);
        registerUser(email, password, name);
        registrationResponse.then().assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("User already exists"));
        loginUser(email, password);
        token = authResponse.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя без пароля ")
    public void registrationUserWithoutPasswordTest(){
        registerUser(email, "", name);
        registrationResponse.then().assertThat()
                .statusCode(403)
                .and()
                .body("message",equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без логина")
    public void registrationUserWithoutLoginTest(){
        registerUser("", password, name);
        registrationResponse.then().assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void registrationUserWithoutNameLoginTest(){
        registerUser(email, password, "");
        registrationResponse.then().assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void deleteUserAfterTests(){
        deleteUser();
    }
}
