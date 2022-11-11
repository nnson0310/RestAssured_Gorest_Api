package ericsson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.ApiCredentials;
import commons.GlobalConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Demo {

    @Getter
    @Setter
    public static class Market<T> {

        @JsonProperty("MarketName")
        private String marketName;

        @JsonProperty("MarketIdentifier")
        private String marketIdentifier;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("IsGlobalMarket")
        private Boolean isGlobalMarket;

        @JsonProperty("SiteLinks")
        private T siteLinks;

        @JsonProperty("LocationLink")
        private LocationLink locationLink;

        @Getter
        @Setter
        public static class LocationLink {

            @JsonProperty("Text")
            private String Text;

            @JsonProperty("Link")
            private String Link;
        }
    }

    @Test
    public void TC_01_Get_All_Api_Market() throws JsonProcessingException {
        RequestSpecification request = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Accept-Charset", "utf-8")
                .basePath("/api")
                .baseUri("https://ericsson.com");

        Response response = request.log().all().get("/getAllMarkets");
        ObjectMapper objectMapper = new ObjectMapper();
        Market[] market = objectMapper.readValue(response.body().asString(), Market[].class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(market[0].getIsGlobalMarket(), equalTo(true));
        assertThat(market[0].getMarketIdentifier(), equalTo("gl"));
        assertThat(market[0].getMarketName(), equalTo("Global"));
    }

    @Test
    public void TC_02_Get_Partner_From_PortFolio_Commerce_MarketPlace() {

        RequestSpecification request = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Accept-Charset", "utf-8")
                .basePath("/portfoliocommerce-marketplace/v1")
                .baseUri("https://apigwy.ericsson.net");

        Response response = request
                .log()
                .all()
                .queryParam("modifiedFrom", "2021-05-01T09:51:19Z")
                .queryParam("pageStart", 1)
                .queryParam("itemsPerPage", 2)
                .get("/partners");

        System.out.println(response.body().asString());
    }

    @Test
    public void TC_03_Get_Category_From_MediaBank() {
        ApiCredentials credentials = null;
        try {
            credentials = new ObjectMapper().readValue(new File(GlobalConstants.pathToTestResource + File.separator + "credentials.json"), ApiCredentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String clientId = credentials.getEricsson().getClientId();
        String username = credentials.getEricsson().getUsername();
        String password = credentials.getEricsson().getPassword();

        RequestSpecification request =  RestAssured.given()
                .baseUri("https://mediabank-acc.ericsson.net:443/admin/api/v1");

        Response response = request
                .auth()
                .preemptive()
                .basic(clientId, "")
                .contentType(ContentType.URLENC)
                .log()
                .all()
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post("/oauth2/token.json");

        JsonPath jsonResponse = response.jsonPath();

        String accessToken = jsonResponse.getString("access_token");
        String refreshToken = jsonResponse.getString("refresh_token");

        Response categoryResponse =  RestAssured.given()
                .baseUri("https://mediabank-acc.ericsson.net:443/admin/api/v1")
                .header("Authorization", "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .log()
                .all()
                .get("/categories.json");

        System.out.println(categoryResponse.statusLine());
        System.out.println(categoryResponse.body().asString());
    }
}
