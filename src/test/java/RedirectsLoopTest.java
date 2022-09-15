import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class RedirectsLoopTest {

    @Test
    public void testRedirectsLoop() {

        String url = "https://playground.learnqa.ru/api/long_redirect";
        System.out.println("Start with Link: " + url);
        int counter=1;

        for (int i=0; i<counter; i++) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();

            int statusCode = response.getStatusCode();

            if (statusCode == 301) {
                Response responseRedirect = RestAssured
                        .given()
                        .redirects()
                        .follow(false)
                        .when()
                        .get(url)
                        .andReturn();

                String link = responseRedirect.getHeader("Location");
                System.out.println("Redirect Link#" + counter + ": " + link);
                url = link;
                counter++;
            }
            else {
                System.out.println("Total Redirects: " + (counter-1));
            }
        }
    }
}