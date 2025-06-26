package cn.bugstack.chatbot.api.domain.zsxq.model.aggregate;

import cn.bugstack.chatbot.api.domain.zsxq.model.res.Resp_data;

/**
 * 未回答的评论的聚合信息
 */

public class TalkTopicId {

    private boolean succeed;

    private Resp_data resp_data;
    private String topicId;
    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public Resp_data getResp_data() {
        return resp_data;
    }

    public void setResp_data(Resp_data resp_data) {
        this.resp_data = resp_data;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}
