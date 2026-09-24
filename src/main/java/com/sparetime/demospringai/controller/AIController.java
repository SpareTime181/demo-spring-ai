package com.sparetime.demospringai.controller;

import com.sparetime.demospringai.repository.ChatHistoryRepository;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequestMapping("/ai")
public class AIController {
    @Resource
    private ChatClient chatClient;
    @Resource
    private ChatClient gameChatClient;
    @Resource
    private ChatClient serviceChatClient;

    @Resource
    private ChatHistoryRepository inMemoryChatHistoryRepository;

    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt, String chatId) {
        inMemoryChatHistoryRepository.save("chat", chatId);
        return chatClient
                .prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    @RequestMapping(value = "/game", produces = "text/html;charset=utf-8")
    public Flux<String> game(String prompt, String chatId) {
        return gameChatClient
                .prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    @RequestMapping(value = "/service", produces = "text/html;charset=utf-8")
    public Flux<String> service(String prompt, String chatId) {
        return serviceChatClient
                .prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

}
