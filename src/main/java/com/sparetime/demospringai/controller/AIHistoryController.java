package com.sparetime.demospringai.controller;

import com.sparetime.demospringai.entity.vo.MessageVO;
import com.sparetime.demospringai.repository.ChatHistoryRepository;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai/history")
public class AIHistoryController {
    @Resource
    private ChatHistoryRepository inMemoryChatHistoryRepository;
    @Resource
    private ChatMemory chatMemory;

    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable("type") String type) {
        return inMemoryChatHistoryRepository.getChatIds(type);
    }

    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        List<Message> messages = chatMemory.get(chatId, Integer.MAX_VALUE);
        if (messages == null) {
            return List.of();
        }
        return messages.stream().map(MessageVO::new).toList();
    }

}
