package cn.bugstack.chatbot.api.domain.zsxq.model.req;

import cn.bugstack.chatbot.api.domain.zsxq.model.aggregate.TalkTopicId;

/**
 * @Classname CommentReq
 * @Description TODO
 * @Date 2025/6/24 10:37
 * @Created by JJJ
 */

public class CommentReq {

    private ReqData req_data;
    private TalkTopicId uncommentedTopicId;

    public CommentReq(ReqData reqData) {
        this.req_data = reqData;
    }

    public ReqData getReq_data() {
        return req_data;
    }

    public void setReq_data(ReqData req_data) {
        this.req_data = req_data;
    }

    public TalkTopicId getUncommentedTopicId() {
        return uncommentedTopicId;
    }

    public void setUncommentedTopicId(TalkTopicId uncommentedTopicId) {
        this.uncommentedTopicId = uncommentedTopicId;
    }
}
