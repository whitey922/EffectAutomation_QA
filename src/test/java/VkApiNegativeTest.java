import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import rest_assured.HTTPCodes;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * User:Anton_Iehorov
 * Date: 12/29/2016
 * Time: 11:10 AM
 */
public class VkApiNegativeTest {
    private final String BASE_URL = "https://api.vk.com/method";
    private final long VK_ID = 657574575;
    private final String METHOD_GET = "get";
    private final String METHOD_FOLLOWERS = "getFollowers";
    private final String METHOD_SUBSCRIPTIONS = "getSubscriptions";
    private final String METHOD_FRIENDS_GET = "/friends.get";

    @Test
    public void pingTestWithDeletedUser() {
        given().when().get(BASE_URL + "/users." + METHOD_GET + "?user_id=" + VK_ID).
                then().statusCode(HTTPCodes.CODE_200);
    }

    @Test
    public void idTest() {
        given().when().get(BASE_URL + "/users." + METHOD_GET + "?user_id=" + VK_ID).
                then().body(containsString("DELETED"));
    }

    @Test
    public void followersTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_FOLLOWERS + "?user_id=" + VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List uidList = jsonPath.get("response.items");
        Assert.assertTrue(uidList.size() == 0);
    }

    @Test
    public void subscriptionsTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_SUBSCRIPTIONS + "?user_id=" + VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> subscriptionsId = jsonPath.get("response.groups.items");
        Assert.assertTrue(subscriptionsId.size() == 0);
    }

    @Test
    public void friendsNonExistTest() {
        given().get(BASE_URL + METHOD_FRIENDS_GET + "?user_id=" + VK_ID).
                then().body(containsString("Access denied: user deactivated"));
    }
}
