package cn.bugstack.chatbot.api;

import org.apache.commons.codec.StringEncoder;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @ClassName test
 * @Author SD.
 * @Description
 * @Date 2025/6/23 20:21
 **/
public class ApiTest {

    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/48885818852858/topics?scope=all&count=20");

        get.addHeader("cookie","_c_WBKFRo=ucS7QCFdaAdUIJpWZ15dDUNJXzdmLyFKzdhrRC2M; abtest_env=product; zsxq_access_token=451D7ECE-90E7-4AC4-AB20-E2C22C6B68CD_AC960507CFE59E14; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22584281515814284%22%2C%22first_id%22%3A%2219787159084295-0f8a4a069a1775-4c657b58-3686400-197871590851e96%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk3ODcxNTkwODQyOTUtMGY4YTRhMDY5YTE3NzUtNGM2NTdiNTgtMzY4NjQwMC0xOTc4NzE1OTA4NTFlOTYiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI1ODQyODE1MTU4MTQyODQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22584281515814284%22%7D%2C%22%24device_id%22%3A%221978716c229143c-0a44d41ab0490b-4c657b58-3686400-1978716c22a258f%22%7D");
        get.addHeader("Content-Type","application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/5125481211851424/comments");

        post.addHeader("cookie","_c_WBKFRo=ucS7QCFdaAdUIJpWZ15dDUNJXzdmLyFKzdhrRC2M; abtest_env=product; zsxq_access_token=451D7ECE-90E7-4AC4-AB20-E2C22C6B68CD_AC960507CFE59E14; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22584281515814284%22%2C%22first_id%22%3A%2219787159084295-0f8a4a069a1775-4c657b58-3686400-197871590851e96%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk3ODcxNTkwODQyOTUtMGY4YTRhMDY5YTE3NzUtNGM2NTdiNTgtMzY4NjQwMC0xOTc4NzE1OTA4NTFlOTYiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI1ODQyODE1MTU4MTQyODQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22584281515814284%22%7D%2C%22%24device_id%22%3A%221978716c229143c-0a44d41ab0490b-4c657b58-3686400-1978716c22a258f%22%7D");
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        String paramJson = "{\"req_data\":{\"text\":\"自己百度\\n\",\"image_ids\":[],\"mentioned_user_ids\":[]}}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.deepseek.com/chat/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer sk-1e016b68555e481d8bf68470d4b78da0");

        String paramJson = "{\n" +
                "        \"model\": \"deepseek-chat\",\n" +
                "        \"messages\": [\n" +
                "          {\"role\": \"user\", \"content\": \"Hello!\"}\n" +
                "        ],\n" +
                "        \"stream\": false\n" +
                "      }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}

