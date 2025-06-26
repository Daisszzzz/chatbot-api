package cn.bugstack.chatbot.api;

import cn.bugstack.chatbot.api.domain.ai.service.Deepseek;
import cn.bugstack.chatbot.api.domain.zsxq.model.aggregate.TalkTopicId;
import cn.bugstack.chatbot.api.domain.zsxq.model.vo.Topics;
import cn.bugstack.chatbot.api.domain.zsxq.service.ZsxqApi;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Classname SpringBootRunTets
 * @Description TODO
 * @Date 2025/6/24 10:50
 * @Created by JJJ
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private ZsxqApi api;

    @Resource
    private Deepseek deepseek;

    @Test
    public void test_zsxqApi() throws IOException {
        TalkTopicId uncommentedTopicId = api.queryTalkTopicId(groupId, cookie);
        logger.info("测试结果:{}", JSON.toJSONString(uncommentedTopicId));

        List<Topics> topics = uncommentedTopicId.getResp_data().getTopics();
        topics.forEach(topic -> {
            String topicId = topic.getTopic_id();
            String talkText = topic.getTalk().getText();
            logger.info("topicId {} talk-text: {}", topicId, talkText);

            try {
                api.comment(groupId,cookie,topicId,talkText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void test_deepseek() throws IOException {
        String response = deepseek.doDeepseek("帮我用python输出hello world");
        logger.info("测试结果:{}", response);
    }
}
