import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;

import static config.Configuration.*;
import static config.Configuration.token;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.ActionWithUserSteps.deleteUser;
import static steps.ActionWithUserSteps.registerUser;
import static steps.AuthSteps.loginUser;

public class GetOrdersForCurrentUserTests {
    @Before
    public void creteUserData() {
        email = "onepunchman40@ya.ru";
        password = "punch";
        name = "Sherlock";
        token = "";
    }

    @Test
    @DisplayName("Получение заказов авторизованным пользователем")
    public void getOrderWithAuth(){
        registerUser(email,password,name);
        loginUser(email, password);
        token = authResponse.jsonPath().getString("accessToken");
        OrderSteps.getOrderList();
        orderListResponse.then().assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Получение заказов неавторизованным пользователем")
    public void getOrderWithoutAuth(){
        OrderSteps.getOrderList();
        orderListResponse.then()
                .statusCode(401)
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void deleteUserAfterTests(){
        deleteUser();
    }
}
