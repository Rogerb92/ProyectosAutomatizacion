import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class ReqRestTest {

    @Test
    public void postloginTest(){
        RestAssured
                .given().log().all()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "    \"email\": \"eve.holt@reqres.in\",\n" +
                            "    \"password\": \"cityslicka\"\n" +
                            "}")
                    .post("https://reqres.in/api/login")
                .then().log().all()
                    .statusCode(200)
                    .body("token",notNullValue());
    }

    @Test
    public void getSingleUserTest(){
        RestAssured
                .given().log().all()
                    .contentType(ContentType.JSON)
                    .get("https://reqres.in/api/users/3")
                .then().log().all()
                    .statusCode(200)
                    .body("data.id",equalTo(2));
    }

}
