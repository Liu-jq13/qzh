package com.kama.notes.utils;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;

import java.util.ArrayList;
import java.util.List;

public class MarkdownAST {
    private final Document markdownAST;
    private final String markdownText;

    // 构造函数：初始化并解析 Markdown 文本
    public MarkdownAST(String markdownText) {
        this.markdownText = markdownText;

        // 创建解析器实例
        Parser parser = Parser.builder().build();
        // 解析 Markdown 文本生成 AST
        this.markdownAST = parser.parse(markdownText);
    }

    // 提取简介，包含 heading 和段落，字数进行限制
    public String extractIntroduction(int maxChars) {
        StringBuilder introText = new StringBuilder();
        // 遍历 AST 节点
        for (Node node : markdownAST.getChildren()) {
            if (node instanceof Heading || node instanceof Paragraph) {
                // 获取节点的文本内容
                String renderedText = getNodeText(node);

                // 截取文本到指定字符数
                int remainingChars = maxChars - introText.length();
                introText.append(renderedText, 0, Math.min(remainingChars, renderedText.length()));

                // 如果达到字符限制，停止提取
                if (introText.length() >= maxChars) {
                    break;
                }
            }
        }

        return introText.toString().trim() + "...";
    }

    // 检查是否包含图片，并返回图片地址
    public List<String> extractImages() {
        List<String> imageUrls = new ArrayList<>();

        // 遍历 AST 节点
        for (Node node : markdownAST.getChildren()) {
            if (node instanceof Image imageNode) {
                // 获取图片地址
                imageUrls.add(imageNode.getUrl().toString());
            }
        }

        return imageUrls;
    }

    // 判断 Markdown 文本是否需要收起（是否包含图片或超过字符数）
    public boolean shouldCollapse(int maxChars) {
        return hasImages() || markdownText.length() > maxChars;
    }

    // 获取精简后的 Markdown 文本
    public String getCollapsedMarkdown() {
        String introText = extractIntroduction(150);
        return introText + "..."; // 返回精简后的 Markdown，添加省略号
    }

    // 获取节点的文本内容
    private String getNodeText(Node node) {
        StringBuilder text = new StringBuilder();

        // 处理 Text 类型节点
        if (node instanceof Text) {
            text.append(((Text) node).getChars());
        }

        // 处理其他类型节点，如强加粗、斜体等
        for (Node child : node.getChildren()) {
            text.append(getNodeText(child));
        }
        return text.toString();
    }

    // 判断 Markdown 文本中是否包含图片
    private boolean hasImages() {
        return !extractImages().isEmpty();
    }
}
