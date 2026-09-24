package com.sparetime.demospringai;

import com.alibaba.cloud.ai.transformer.splitter.RecursiveCharacterTextSplitter;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author Qiu Yifan
 * @date 2026/9/24 下午3:32
 * @desc
 */
@SpringBootTest
public class VectorStoreTest {
    @Autowired
    private VectorStore vectorStore;

    // 自定义递归切分器：块大小 512，使用中文优先的分隔符列表
    RecursiveCharacterTextSplitter customSplitter = new RecursiveCharacterTextSplitter(
            512,  // chunkSize：每个文本块的目标最大字符数
            new String[]{
                    "\n\n",   // 段落分隔（最高优先级）
                    "\n",     // 换行
                    "。",     // 中文句号
                    "！",     // 中文叹号
                    "？",     // 中文问号
                    "；",     // 中文分号
                    "，",     // 中文逗号
                    " "       // 空格（最低优先级）
            }
    );

    @Test
    public void testVectorStore() {
        // 1. 加载 PDF 资源
        Resource pdfResource = new ClassPathResource("中二知识笔记.pdf");

        // 2. 构建 PDF 读取配置（可选，用于精细控制）
        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)          // 页面上边距设为 0，避免顶部空白干扰段落识别
                .withPageBottomMargin(0)       // 页面下边距设为 0
                .withPagesPerDocument(1)       // 每个 Document 包含 1 页
                .build();

        // 3. 使用 ParagraphPdfDocumentReader 按段落读取
        // 该读取器基于 PDF目录（TOC）或书签信息，将PDF拆分为段落级 Document
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource, config);

        List<Document> docs = reader.read();

        List<Document> splitDocs = customSplitter.split(docs);

        vectorStore.add(splitDocs);

        SearchRequest request = SearchRequest.builder()
                .query("论语中教育的目的是什么")
                .topK(2)
                .similarityThreshold(0.6)
                .filterExpression("file_name == '中二知识笔记.pdf'")
                .build();

        List<Document> documents = vectorStore.similaritySearch(request);
        for (Document document : documents) {
            System.out.println(document.getScore());
            System.out.println(document.getMetadata());
            System.out.println(document.getText());
        }
    }

}