import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class StringLengthTest {

    @Test
    public void testStringLength() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_text")
                .andReturn();

        assertTrue (response.asByteArray().length>15, "String length equal or less then 15");
    }
}