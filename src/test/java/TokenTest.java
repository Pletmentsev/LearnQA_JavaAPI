import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class TokenTest {

    @Test
    public void testToken() throws InterruptedException {

        Response getTokenAndSecond = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        String token = getTokenAndSecond.jsonPath().getString("token"); // needs to be commented to simulate case with non-existent token
        /*String token = "QNwoTOxojNxAiNx0SOw0iMyOnT";*/ // needs to be uncommented to simulate case with non-existent token

        int second = getTokenAndSecond.jsonPath().getByte("seconds");

        Response requestWithToken = RestAssured
                    .given()
                    .queryParams("token", token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .andReturn();

        if(requestWithToken.jsonPath().getString("error") != null) { //check for case with non-existent token
            String error = requestWithToken.jsonPath().getString("error");
            System.out.println(error);
        }

        else {
            String status = requestWithToken.jsonPath().getString("status");

            if (status.equals("Job is NOT ready")) { //check for status: Job is NOT ready
                System.out.println("First request with status: " + status + " - Done");

                Thread.sleep(second * 1000);
                Response finalRequest = RestAssured
                        .given()
                        .queryParams("token", token)
                        .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                        .andReturn();

                String finalStatus = finalRequest.jsonPath().getString("status");
                String finalResult = finalRequest.jsonPath().getString("result");

                if (finalStatus.equals("Job is ready")) { //check for status: Job is ready
                    System.out.println("Final request with status: " + finalStatus + " - Done");
                    System.out.println("Result: " + finalResult);
                }
                else {
                    System.out.println("Something wrong with status: Job is ready ");
                }
            }
            else {
                System.out.println("Something wrong with status: Job is NOT ready");
            }
        }
    }
}

