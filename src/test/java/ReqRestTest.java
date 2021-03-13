import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

public class ReqRestTest {

    @Test
    public void loginTest(){

        RestAssured
                .given().log().all()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "    \"email\": \"eve.holt@reqres.in\",\n" +
                            "    \"password\": \"cityslicka\"\n" +
                            "}")
                    .post("https://reqres.in/api/login")
                .then().log().all()
                    .extract().toString();
    }


}
