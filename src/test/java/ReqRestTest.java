import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReqRestTest {

    @Before
    public void setup(){
        RestAssured.baseURI="https://reqres.in";
        RestAssured.basePath="/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void postloginTest(){
         given()
            .body("{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                 "    \"password\": \"cityslicka\"\n" +
                 "}")
            .post("login")
         .then()
            .statusCode(200)
            .body("token",notNullValue());
    }

    @Test
    public void getSingleUserTest(){
        given()
            .get("users/2")
        .then()
            .statusCode(200)
            .body("data.id",equalTo(2));
    }

    @Test
    public void deleteUserTest(){
        given()
            .delete("users/2")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void patchUserTest(){
        String nombreActualizado=
            given()
            .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                     "}")
                .patch("users/2")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString("name");

        assertThat(nombreActualizado, equalTo("morpheus"));
    }
    @Test
    public void pautUserTest(){
        String trabajoActualizado=
                given()
                        .when()
                        .body("{\n" +
                                "    \"name\": \"morpheus\",\n" +
                                "    \"job\": \"zion resident\"\n" +
                                "}")
                        .put("users/2")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract().jsonPath().getString("job");

        assertThat(trabajoActualizado, equalTo("zion resident"));
    }

    @Test
    public void getAllUsersTest(){
        Response response = given().get("users?page=2");

        Headers headers = response.getHeaders();
        int statusCode = response.getStatusCode();
        String body = response.getBody().toString();
        String contentType = response.getContentType();

        assertThat(statusCode,equalTo(HttpStatus.SC_OK));
        System.out.println("Body: "+body);
        System.out.println("********");
        System.out.println("Content Type: "+contentType);
        System.out.println("********");
        System.out.println("Headers: "+headers.toString());
        System.out.println("********");
        System.out.println(headers.get("Content-Type"));
        System.out.println("********");
        System.out.println(headers.get("Transfer-Encoding"));
    }


}
