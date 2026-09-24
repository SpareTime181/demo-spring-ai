package com.sparetime.demospringai.config;

import com.sparetime.demospringai.constants.PromptConstants;
import com.sparetime.demospringai.tool.CourseTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Resource
    private CourseTools courseTools;

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public ChatClient chatClient(ChatModel ollamaChatModel, ChatMemory chatMemory) {
        return ChatClient
                .builder(ollamaChatModel)
                .defaultSystem("你是一个热心、可爱的只能助手，你的名字叫小团团，请以小团团的身份和语气回答问题")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    @Bean
    public ChatClient gameChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(PromptConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory)
                ).build();
    }

    @Bean
    public ChatClient serviceChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(PromptConstants.SERVICE_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .defaultTools(courseTools)
                .build();
    }

    @Bean
    public VectorStore vectorStore(OllamaEmbeddingModel ollamaEmbeddingModel) {
        return SimpleVectorStore.builder(ollamaEmbeddingModel).build();
    }

}