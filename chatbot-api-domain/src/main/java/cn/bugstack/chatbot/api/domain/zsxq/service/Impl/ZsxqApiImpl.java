package cn.bugstack.chatbot.api.domain.zsxq.service.Impl;

import cn.bugstack.chatbot.api.domain.zsxq.model.aggregate.TalkTopicId;
import cn.bugstack.chatbot.api.domain.zsxq.model.req.CommentReq;
import cn.bugstack.chatbot.api.domain.zsxq.model.req.ReqData;
import cn.bugstack.chatbot.api.domain.zsxq.model.res.CommentRes;
import cn.bugstack.chatbot.api.domain.zsxq.service.ZsxqApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Classname ZsxqApiImpl
 * @Description TODO
 * @Date 2025/6/24 10:20
 * @Created by JJJ
 */
@Service
public class ZsxqApiImpl implements ZsxqApi {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ZsxqApiImpl.class);


    @Override
    public TalkTopicId queryTalkTopicId(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/"+ groupId + "/topics?scope=all&count=20");

        get.addHeader("cookie",cookie);
        get.addHeader("Content-Type","application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            log.info("拉取评论数据: groupId: {} jsonStr: {}", groupId, jsonStr);
            return JSON.parseObject(jsonStr, TalkTopicId.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean comment(String groupId, String cookie, String topicId, String text) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/comments");

        post.addHeader("cookie",cookie);
        post.addHeader("Content-Type","application/json; charset=UTF-8");
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36 Edg/137.0.0.0");

        //String paramJson = "{\"req_data\":{\"text\":\"自己百度\\n\",\"image_ids\":[],\"mentioned_user_ids\":[]}}";

        CommentReq commentReq = new CommentReq(new ReqData(text));

        String paramJson = JSONObject.toJSONString(commentReq);
        System.out.println(paramJson);
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            log.info("追评星球评论的结果: topicId: {} groupId: {} jsonStr: {}", topicId, groupId, jsonStr);
            CommentRes commentRes = JSON.parseObject(jsonStr, CommentRes.class);
            return commentRes.isSucceed();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
