package cn.bugstack.chatbot.api.application.job;

import cn.bugstack.chatbot.api.domain.ai.service.Deepseek;
import cn.bugstack.chatbot.api.domain.zsxq.model.aggregate.TalkTopicId;
import cn.bugstack.chatbot.api.domain.zsxq.model.vo.Topics;
import cn.bugstack.chatbot.api.domain.zsxq.service.ZsxqApi;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @Classname ChatbotSchedule
 * @Description Deepseek问答任务
 * @Date 2025/6/25 12:11
 * @Created by JJJ
 */

@EnableScheduling
@Configuration
public class ChatbotSchedule {

    Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private ZsxqApi api;
    @Resource
    private Deepseek deepseek;

    @Scheduled(cron = "0/30 * * * * ?")
    public void run() {
        try {
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中");
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour > 22 || hour < 7) {
                logger.info("不在工作时间段");
                return;
            }

            TalkTopicId uncommentedTopicId = api.queryTalkTopicId(groupId, cookie);
            logger.info("测试结果:{}", JSON.toJSONString(uncommentedTopicId));

            List<Topics> topics = uncommentedTopicId.getResp_data().getTopics();

            if(null == topics || topics.isEmpty()){
                return;
            }

            for (Topics topic : topics) {
                // 检索问题
                int commentsCount = topic.getComments_count();
                if(commentsCount != 0) continue;

                // AI回答
                String answer = deepseek.doDeepseek(topic.getTalk().getText());

                // 问题回复
                boolean status = api.comment(groupId, cookie, topic.getTopic_id(), answer);
                logger.info("编号: {} 问题: {} 回答: {} 状态: {}",
                        topic.getTopic_id(), topic.getTalk().getText(), answer, status);
            }
        }
        catch (Exception e) {
            logger.error("自动回答评论异常", e);
        }
    }

}
