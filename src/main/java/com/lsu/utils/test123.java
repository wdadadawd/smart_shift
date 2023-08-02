package com.lsu.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;


import java.time.Duration;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;

import static com.theokanning.openai.service.OpenAiService.*;

/**
 * @author zt
 * @create 2023-07-30 20:07
 */
public class test123 {
    public static void main(String[] args) {
        String token = "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";
        ObjectMapper mapper = defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("data.bilibili.com", 443));
        OkHttpClient client = defaultClient(token, Duration.ofMinutes(1))
                .newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        OpenAiService service = new OpenAiService(api);
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel("gpt-3.5-turbo-0301");
        chatCompletionRequest.setTemperature(0.5);
        chatCompletionRequest.setMaxTokens(1000);
        chatCompletionRequest.setTopP(1.0);
        chatCompletionRequest.setFrequencyPenalty(0.0);
        chatCompletionRequest.setPresencePenalty(0.0);
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("user","你好"));
        chatCompletionRequest.setMessages(chatMessages);

        ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);
        System.out.println(chatCompletion.toString());
    }
}
