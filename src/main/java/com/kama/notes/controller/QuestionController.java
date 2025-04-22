package com.kama.notes.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.dto.question.CreateQuestionBody;
import com.kama.notes.model.dto.question.QuestionQueryParam;
import com.kama.notes.model.dto.question.SearchQuestionBody;
import com.kama.notes.model.dto.question.UpdateQuestionBody;
import com.kama.notes.model.vo.question.CreateQuestionVO;
import com.kama.notes.model.vo.question.QuestionNoteVO;
import com.kama.notes.model.vo.question.QuestionUserVO;
import com.kama.notes.model.vo.question.QuestionVO;
import com.kama.notes.service.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 用户端获取问题列表
     *
     * @param queryParams 查询参数，用于过滤问题列表（如关键词、分类等）
     * @return 包含用户可见问题的视图对象列表的响应
     */
    @GetMapping("/questions")
    public ApiResponse<List<QuestionUserVO>> userGetQuestions(@Valid QuestionQueryParam queryParams) {
        return questionService.userGetQuestions(queryParams);
    }

    /**
     * 用户端搜索问题
     *
     * @param body 包含搜索关键词的请求体
     * @return 包含搜索结果的视图对象列表的响应
     */
    @PostMapping("/questions/search")
    public ApiResponse<List<QuestionVO>> searchQuestions(@Valid @RequestBody SearchQuestionBody body) {
        return questionService.searchQuestions(body);
    }

    /**
     * 用户端获取单个问题详情
     *
     * @param questionId 问题ID，必须为正整数
     * @return 包含问题详情及关联笔记的视图对象的响应
     */
    @GetMapping("/questions/{questionId}")
    public ApiResponse<QuestionNoteVO> userGetQuestion(@Min(value = 1, message = "questionId 必须为正整数")
                                                       @PathVariable Integer questionId) {
        return questionService.userGetQuestion(questionId);
    }

    /**
     * 管理端获取问题列表
     *
     * @param queryParams 查询参数，用于过滤问题列表（如关键词、时间范围等）
     * @return 包含所有问题的视图对象列表的响应
     */
    @GetMapping("/admin/questions")
    public ApiResponse<List<QuestionVO>> getQuestions(@Valid QuestionQueryParam queryParams) {
        return questionService.getQuestions(queryParams);
    }

    /**
     * 管理端创建新问题
     *
     * @param createQuestionBody 创建问题的请求体，包含问题的标题、内容等信息
     * @return 包含新创建问题视图对象的响应
     */
    @PostMapping("/admin/questions")
    public ApiResponse<CreateQuestionVO> createQuestion(@Valid @RequestBody CreateQuestionBody createQuestionBody) {
        return questionService.createQuestion(createQuestionBody);
    }

    /**
     * 管理端更新问题
     *
     * @param questionId         问题ID，必须为正整数
     * @param updateQuestionBody 更新问题的请求体，包含要更新的字段和值
     * @return 空视图对象的响应，表示更新操作成功
     */
    @PatchMapping("/admin/questions/{questionId}")
    public ApiResponse<EmptyVO> updateQuestion(@Min(value = 1, message = "questionId 必须为正整数")
                                               @PathVariable Integer questionId,
                                               @Valid @RequestBody UpdateQuestionBody updateQuestionBody) {
        return questionService.updateQuestion(questionId, updateQuestionBody);
    }

    /**
     * 管理端删除问题
     *
     * @param questionId 问题ID，必须为正整数
     * @return 空视图对象的响应，表示删除操作成功
     */
    @DeleteMapping("/admin/questions/{questionId}")
    public ApiResponse<EmptyVO> deleteQuestion(@Min(value = 1, message = "questionId 必须为正整数")
                                               @PathVariable Integer questionId) {
        return questionService.deleteQuestion(questionId);
    }
}
