package cn.bugstack.chatbot.api.domain.zsxq.service;


import cn.bugstack.chatbot.api.domain.zsxq.model.aggregate.TalkTopicId;

import java.io.IOException;

public interface ZsxqApi {


    TalkTopicId queryTalkTopicId(String groupId, String cookie) throws IOException;

    boolean comment(String groupId, String cookie, String topicId, String text) throws IOException;
}
