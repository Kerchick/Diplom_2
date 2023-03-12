import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static config.Configuration.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.ActionWithUserSteps.*;
import static steps.AuthSteps.*;


public class AuthUserTests {
    @Before
    public void creteUserData(){
        email = "10xvostov@naruto.ru";
        password = "Jinchuriki";
        name = "Naruto";
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void successAuthTest(){
        registerUser(email, password, name);
        loginUser(email,password);
        authResponse.then().assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Авторизация без логина")
    public void authWithoutLoginTest(){
        loginUser("", password);
        authResponse.then().assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация без пароля ")
    public void authWithoutPasswordTest(){
        loginUser(email,"");
        authResponse.then().assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void deleteUserAfterTests(){
        deleteUser();
    }
}
