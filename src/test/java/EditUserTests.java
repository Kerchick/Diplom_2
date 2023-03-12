import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static config.Configuration.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.ActionWithUserSteps.*;
import static steps.AuthSteps.*;


public class EditUserTests {
    @Before
    public void creteUserData(){
        email = "steave45@google.com";
        password = "creaper";
        name = "Bogdan";
        token = "";
        registerUser(email,password,name);
    }

    @Test
    @DisplayName("Изменение email")
    public void editMailTest(){
        loginUser(email,password);
        token =  authResponse.jsonPath().getString("accessToken");
        editUser("og" + email, password, name);
        editUserResponse.then()
                .statusCode(200)
                .and()
                .body("user.email", equalTo("og" + email));
    }
    @Test
    @DisplayName("Изменение password")
    public void editPasswordTest(){
        loginUser(email,password);
        token =  authResponse.jsonPath().getString("accessToken");
        editUser(email,  password + 13, name);
        editUserResponse.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Изменение name")
    public void editNameTest(){
        loginUser(email,password);
        token =  authResponse.jsonPath().getString("accessToken");
        editUser(email, password, "gav" + name);
        editUserResponse.then()
                .statusCode(200)
                .and()
                .body("user.name", equalTo("gav" + name));
    }

    @Test
    @DisplayName("Изменение данных без авторизации")
    public void editUserWithoutAuth(){
        editUser(email, password, name);
        editUserResponse.then()
                .statusCode(401)
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void deleteUserAfterTests(){
        deleteUser();
    }
}
