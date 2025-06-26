package cn.bugstack.chatbot.api.domain.ai.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

public interface Deepseek {

    String doDeepseek(String question) throws IOException;
}
