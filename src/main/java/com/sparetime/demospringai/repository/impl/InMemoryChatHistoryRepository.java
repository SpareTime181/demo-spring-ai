package com.sparetime.demospringai.repository.impl;

import com.sparetime.demospringai.repository.ChatHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryChatHistoryRepository implements ChatHistoryRepository {

    Map<String, LinkedHashSet<String>> chatHistory = new HashMap<>();

    @Override
    public void save(String type, String chatId) {
        LinkedHashSet<String> chatIds = chatHistory.computeIfAbsent(type, k -> new LinkedHashSet<>());
        chatIds.add(chatId);
    }

    @Override
    public List<String> getChatIds(String type) {
        return new ArrayList<>(chatHistory.getOrDefault(type, new LinkedHashSet<>()));
    }
}