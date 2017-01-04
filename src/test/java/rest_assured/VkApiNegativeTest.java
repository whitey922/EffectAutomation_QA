package rest_assured;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import constans.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import rest_assured.utils.VkApiHelper;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static constans.MainUrl.*;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * User:Anton_Iehorov
 * Date: 12/29/2016
 * Time: 11:10 AM
 */
public final class VkApiNegativeTest {

    @Test
    public void pingTestWithDeletedUser() {
        given().when().get(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + User.UncorrectedUser.VK_ID).
                then().statusCode(HTTPCodes.CODE_200);
    }

    @Test
    public void correctIdTest() {
        given().when().get(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + User.UncorrectedUser.VK_ID).
                then().body(containsString("DELETED"));
    }

    @Test
    public void followersTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_FOLLOWERS + USER_ID_METHOD + User.UncorrectedUser.VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List uidList = jsonPath.get("response.items");
        Assert.assertTrue(VkApiHelper.isEmpty(uidList.size()));
    }

    @Test
    public void subscriptionsTest() {
        Response response = given()
                .get(BASE_URL + "/users." + METHOD_SUBSCRIPTIONS + USER_ID_METHOD + User.UncorrectedUser.VK_ID);
        JsonPath jsonPath = JsonPath.from(response.body().asString());
        List<Integer> subscriptionsId = jsonPath.get("response.groups.items");
        Assert.assertTrue(VkApiHelper.isEmpty(subscriptionsId.size()));
    }

    @Test
    public void friendsNonExistTest() {
        given().get(BASE_URL + METHOD_FRIENDS_GET + USER_ID_METHOD + User.UncorrectedUser.VK_ID).
                then().body(containsString("Access denied: user deactivated"));
    }
}
