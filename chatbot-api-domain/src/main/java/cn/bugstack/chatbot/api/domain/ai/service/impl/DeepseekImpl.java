package cn.bugstack.chatbot.api.domain.ai.service.impl;

import cn.bugstack.chatbot.api.domain.ai.service.Deepseek;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Classname DeepseekAIImpl
 * @Description TODO
 * @Date 2025/6/25 9:24
 * @Created by JJJ
 */

@Service
public class DeepseekImpl implements Deepseek {

    private Logger logger = LoggerFactory.getLogger(DeepseekImpl.class);

    @Value("${chatbot-api.deepseekKey}")
    private String DeepseekKey;


    @Override
    public String doDeepseek(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.deepseek.com/chat/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer " + DeepseekKey);

        String paramJson = "{\n" +
                "        \"model\": \"deepseek-chat\",\n" +
                "        \"messages\": [\n" +
                "          {\"role\": \"user\", \"content\": \"" + question + "\"}\n" +
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

        return "";
    }
}
