import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import rest_assured.HTTPCodes;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;


/**
 * User:Anton_Iehorov
 * Date: 12/28/2016
 * Time: 3:31 PM
 */
public class VkApiPositiveTest {

    private static final int FIRST_USER = 0;
    private final String BASE_URL = "https://api.vk.com/method";
    private final long VK_ID = 23338953;
    private final String METHOD_GET = "get";
    private final String METHOD_FOLLOWERS = "getFollowers";
    private final String METHOD_SUBSCRIPTIONS = "getSubscriptions";
    private final String METHOD_FRIENDS_GET = "/friends.get";

    @Test
    public void pingTest() {
        given().when().get(BASE_URL + "/users." + METHOD_GET + "?user_id=" + VK_ID).
                then().statusCode(HTTPCodes.CODE_200);
    }

    @Test
    public void idTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_GET + "?user_id=" + VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> uidList = jsonPath.get("response.uid");
        Assert.assertEquals(uidList.get(FIRST_USER).intValue(), VK_ID);
    }

    @Test
    public void followersTest() {
        Response response = given()
                .get(BASE_URL +"/users."+ METHOD_FOLLOWERS + "?user_id=" + VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> followersId = jsonPath.get("response.items");
        Assert.assertTrue(followersId.size() != 0);
    }

    @Test
    public void subscriptionsTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_SUBSCRIPTIONS + "?user_id=" + VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> subscriptionsId = jsonPath.get("response.groups.items");
        Assert.assertTrue(subscriptionsId.contains(90767168));
    }

    @Test
    public void friendExistTest() {
        Response response = given()
                .get(BASE_URL + METHOD_FRIENDS_GET + "?user_id=" + VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> subscriptionsId = jsonPath.get("response");
        Assert.assertTrue(subscriptionsId.contains(5983205));
    }


}
