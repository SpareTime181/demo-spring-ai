package com.sparetime.demospringai.repository;

import java.util.List;

public interface ChatHistoryRepository {

    /**
     * 保存会话类型和id
     *
     * @param type 会话类型
     * @param chatId 会话id
     */
    void save(String type, String chatId);

    /**
     * 获取会话id集合
     *
     * @param type 类型
     * @return 获取会话id集合
     */
    List<String> getChatIds(String type);

}
