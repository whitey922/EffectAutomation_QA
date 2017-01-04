package rest_assured;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import constans.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static constans.MainUrl.*;
/**
 * User:Anton_Iehorov
 * Date: 12/28/2016
 * Time: 3:31 PM
 */
public final class VkApiPositiveTest {
    private static final int FIRST_USER = 0;
    
    @Test
    public void pingTest() {
        given().when().get(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + User.CorrectUser.VK_ID).
                then().statusCode(HTTPCodes.CODE_200);
    }

    @Test
    public void correctIdTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + User.CorrectUser.VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> uidList = jsonPath.get("response.uid");
        Assert.assertEquals(uidList.get(FIRST_USER).intValue(), User.CorrectUser.VK_ID);
    }

    @Test
    public void followersTest() {
        Response response = given()
                .get(BASE_URL +"/users."+ METHOD_FOLLOWERS + USER_ID_METHOD + User.CorrectUser.VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> followersId = jsonPath.get("response.items");
        Assert.assertTrue(followersId.contains((int)User.CorrectUser.FOLLOWER_ID));
    }

    @Test
    public void subscriptionsTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_SUBSCRIPTIONS + USER_ID_METHOD + User.CorrectUser.VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> subscriptionsId = jsonPath.get("response.groups.items");
        Assert.assertTrue(subscriptionsId.contains((int)User.CorrectUser.SUBSCRIBER_ID));
    }

    @Test
    public void friendExistTest() {
        Response response = given()
                .get(BASE_URL + METHOD_FRIENDS_GET + USER_ID_METHOD + User.CorrectUser.VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> subscriptionsId = jsonPath.get("response");
        Assert.assertTrue(subscriptionsId.contains((int)User.CorrectUser.FRIEND_ID));
    }


}
