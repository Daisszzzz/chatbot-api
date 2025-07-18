package cn.bugstack.chatbot.api.domain.ai.model.vo;

/**
 * @Classname Choices
 * @Description 选择模型
 * @Date 2025/6/25 9:26
 * @Created by JJJ
 */

public class Choices {
    private int index;

    private Message message;

    private String logprobs;

    private String finish_reason;

    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
    public void setMessage(Message message){
        this.message = message;
    }
    public Message getMessage(){
        return this.message;
    }
    public void setLogprobs(String logprobs){
        this.logprobs = logprobs;
    }
    public String getLogprobs(){
        return this.logprobs;
    }
    public void setFinish_reason(String finish_reason){
        this.finish_reason = finish_reason;
    }
    public String getFinish_reason(){
        return this.finish_reason;
    }
}
