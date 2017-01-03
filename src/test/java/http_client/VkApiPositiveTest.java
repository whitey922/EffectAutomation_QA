package http_client;

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
public class VkApiPositiveTest {

    private final long VK_ID = 23338953;
    private final String USER_NAME = "Антон";
    private final String USER_SURNAME = "Егоров";

    private HttpClient client;
    private HttpGet request;
    private HttpResponse response;

    @BeforeMethod
    public void setUp() {
        client = HttpClientBuilder.create().build();
    }

    @Test(priority = 1)
    public void pingTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + VK_ID);
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }

        Assert.assertEquals(CODE_200, response.getStatusLine().getStatusCode());
        System.out.println(Thread.currentThread().getId());
    }

    @Test(priority = 2)
    public void correctIdTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_GET + USER_ID_METHOD + VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            responseBody = rd.readLine();
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }
        System.out.println(Thread.currentThread().getId());

        Assert.assertTrue(responseBody.contains(USER_NAME) && responseBody.contains(USER_SURNAME));
    }

    @Test(priority = 3)
    public void followersTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_FOLLOWERS + USER_ID_METHOD + VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            responseBody = rd.readLine();
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }
        System.out.println(Thread.currentThread().getId());
        Assert.assertTrue(responseBody.contains("390248447"));
    }

    @Test(priority = 4)
    public void subscriptionsTest() {
        request = new HttpGet(BASE_URL + "/users." + METHOD_SUBSCRIPTIONS + USER_ID_METHOD + VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            responseBody = rd.readLine();
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }
        System.out.println(Thread.currentThread().getId());
        Assert.assertTrue(responseBody.contains("33393308"));
    }

    @Test(priority = 5)
    public void friendExistTest() {
        request = new HttpGet(BASE_URL + METHOD_FRIENDS_GET + USER_ID_METHOD + VK_ID);
        String responseBody = "";
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            responseBody = rd.readLine();
        } catch (IOException e) {
            throw new ConnectionException("Connection error!");
        }
        System.out.println(Thread.currentThread().getId());
        Assert.assertTrue(responseBody.contains("3171302"));
    }

}
