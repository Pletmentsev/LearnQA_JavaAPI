import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class ParseJsonTest {

    @Test
    public void testParsingJson() {


        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();

        String answer = response.jsonPath().getString("messages.message[1]");
        System.out.println(answer);
    }
}