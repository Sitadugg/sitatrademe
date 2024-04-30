import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import java.io.File;

public class ApiTests {

    private ApiHelper apiHelper;

    @Before
    public void setUp() {
        apiHelper = new ApiHelper("https://api.trademe.co.nz");
    }

    @Test
    public void testGetLatestListings() {

        String fileFormat = "json";

        String endpoint = String.format("/v1/Listings/Latest.%s", fileFormat);
        Response response = apiHelper.get(endpoint);

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.getContentType());

        // Verify TotalCount
        assertEquals(123, response.jsonPath().getInt("TotalCount"));

        // Verify Page
        assertEquals(123, response.jsonPath().getInt("Page"));

        // Verify PageSize
        assertEquals(123, response.jsonPath().getInt("PageSize"));

        // Verify attributes of the first listing in the List
        assertEquals(123, response.jsonPath().getInt("List[0].ListingId"));
        assertEquals("ABC", response.jsonPath().getString("List[0].Title"));
        assertEquals("ABC", response.jsonPath().getString("List[0].Category"));
        assertEquals(123.0, response.jsonPath().getDouble("List[0].StartPrice"), 0.01);
        assertEquals(123.0, response.jsonPath().getDouble("List[0].BuyNowPrice"), 0.01);

    }

    @Test
    public void testCreateListing() {
        // Load JSON payload from file
        File payloadFile = new File("src/test/resources/payload.json");

        // Send POST request to create listing
        Response response = given()
                .contentType("application/json")
                .body(payloadFile)
                .post("/v1/Selling.json");

        // Verify response status code
        response.then().statusCode(200);
        // Verify response body
        response.then().body("Success", equalTo(false));
        response.then().body("Description", equalTo("ABC"));
        response.then().body("ListingId", equalTo(123));
        // Verify Listing details
        response.then().body("Listing", notNullValue());
        // Verify Listing Group Id
        response.then().body("ListingGroupId", equalTo(123));
    }

}
