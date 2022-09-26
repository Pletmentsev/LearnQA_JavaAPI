import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CookieMethodTest {
    @Test
    public void testCookieMethod() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        assertTrue (response.getHeaders().hasHeaderWithName("Set-Cookie"), "Response doesn't have 'Set-Cookie' headers");
        assertEquals ("hw_value", response.getCookie("HomeWork"), "Cookies not equal expected result");
    }
}