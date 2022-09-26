import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeaderMethodTest {
    @Test
    public void testHeaderMethod() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        assertTrue (response.getHeaders().hasHeaderWithName("x-secret-homework-header"), "Response doesn't have 'x-secret-homework-header' in headers");
        assertEquals ("Some secret value", response.getHeader("x-secret-homework-header"), "x-secret-homework-header is not equal to expected result");
    }
}