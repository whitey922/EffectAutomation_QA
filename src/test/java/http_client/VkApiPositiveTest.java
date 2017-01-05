package http_client;

import constans.User;
import exception.ConnectionException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static constans.MainUrl.*;
import static rest_assured.HTTPCodes.CODE_200;

/**
 * User:Anton_Iehorov
 * Date: 1/3/2017
 * Time: 3:20 PM
 */
public final class VkApiPositiveTest {

    private HttpClient client;
    private HttpGet request;
    private HttpResponse response;

    @BeforeMethod
    public void setUp() {
        client = HttpClientBuilder.create().build();
    }

    @Test(priority = 1)
    public void pingTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + User.CorrectUser.VK_ID);
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }

        Assert.assertEquals(CODE_200, response.getStatusLine().getStatusCode());
    }

    @Test(priority = 2)
    public void correctIdTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + User.CorrectUser.VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            try(BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))){
                responseBody = rd.readLine();
            }
            catch (IOException e) {
                throw new ConnectionException("Read from service error!");
            }
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }

        Assert.assertTrue(
                responseBody.contains(User.CorrectUser.USER_NAME) &&
                        responseBody.contains(User.CorrectUser.USER_SURNAME));
    }

    @Test(priority = 3)
    public void followersTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_FOLLOWERS + USER_ID_METHOD + User.CorrectUser.VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            try(BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))){
                responseBody = rd.readLine();
            }
            catch (IOException e) {
                throw new ConnectionException("Read from service error!");
            }
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }
        Assert.assertTrue(responseBody.contains(String.valueOf(User.CorrectUser.FOLLOWER_ID)));
    }

    @Test(priority = 4)
    public void subscriptionsTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_SUBSCRIPTIONS + USER_ID_METHOD + User.CorrectUser.VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            try(BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))){
                responseBody = rd.readLine();
            }
            catch (IOException e) {
                throw new ConnectionException("Read from service error!");
            }
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }
        Assert.assertTrue(responseBody.contains(String.valueOf(User.CorrectUser.SUBSCRIBER_ID)));
    }

    @Test(priority = 5)
    public void friendExistTest() {
        request = new HttpGet(BASE_URL + METHOD_FRIENDS_GET + USER_ID_METHOD + User.CorrectUser.VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            try(BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))){
                responseBody = rd.readLine();
            }
            catch (IOException e) {
                throw new ConnectionException("Read from service error!");
            }

        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }
        Assert.assertTrue(responseBody.contains(String.valueOf(User.CorrectUser.FRIEND_ID)));
    }

}
