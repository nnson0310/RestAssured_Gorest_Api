package imgur;

import commons.ApiCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.MethodHelper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Demo {

    private WebDriver driver;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
    private String redirectUri;
    private String authorizationCode;
    private WebDriverWait explicitWait;

    @BeforeMethod
    public void setUp() {

        ApiCredentials credentials = null;
        try {
            credentials = new ObjectMapper().readValue(new File(GlobalConstants.pathToTestResource + File.separator + "credentials.json"), ApiCredentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clientId = credentials.getImgur().getClientId();
        clientSecret = credentials.getImgur().getClientSecret();
        redirectUri = credentials.getImgur().getRedirectUri();
        username = credentials.getImgur().getUsername();
        password = credentials.getImgur().getPassword();

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        String imgurOAuth2Url = "https://api.imgur.com/oauth2/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;

        driver.get(imgurOAuth2Url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(25));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#username")));
        driver.findElement(By.cssSelector("input#username")).sendKeys(username);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#password")));
        driver.findElement(By.cssSelector("input#password")).sendKeys(password);
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.access-options button#allow")));
        driver.findElement(By.cssSelector("div.access-options button#allow")).click();

        MethodHelper.sleepInSeconds(5);

        authorizationCode = driver.getCurrentUrl().split("code=")[1];
        driver.quit();
    }

    @Test
    public void TC_01_Get_Imgur_Images() {
        Response accessTokenResponse = RestAssured.given().urlEncodingEnabled(false)
                .param("code", authorizationCode)
                .param("grant_type", "authorization_code")
                .param("client_id", clientId)
                .param("client_secret", clientSecret)
                .when().log().all()
                .post("https://api.imgur.com/oauth2/token");

        String accessToken = new JsonPath(accessTokenResponse.asString()).getString("access_token");

        Response imageDataResponse = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .when().log().all()
                .get("https://api.imgur.com/3/account/me/images");

        System.out.println(imageDataResponse.body().asString());
        assertThat(imageDataResponse.statusCode(), equalTo(200));
    }
}
