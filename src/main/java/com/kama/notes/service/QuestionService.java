package com.kama.notes.service;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.dto.question.CreateQuestionBody;
import com.kama.notes.model.dto.question.QuestionQueryParam;
import com.kama.notes.model.dto.question.SearchQuestionBody;
import com.kama.notes.model.dto.question.UpdateQuestionBody;
import com.kama.notes.model.entity.Question;
import com.kama.notes.model.vo.question.CreateQuestionVO;
import com.kama.notes.model.vo.question.QuestionNoteVO;
import com.kama.notes.model.vo.question.QuestionUserVO;
import com.kama.notes.model.vo.question.QuestionVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface QuestionService {
    /**
     * 根据问题 ID 获取问题信息
     * @param questionId 问题 ID
     * @return 问题信息
     */
    Question findById(Integer questionId);

    /**
     * 根据问题 ID 批量获取问题信息
     *
     * @param questionIds 问题 ID 列表
     * @return 问题信息
     */
    Map<Integer, Question> getQuestionMapByIds(List<Integer> questionIds);

    /**
     * 根据查询参数获取问题列表
     *
     * @param queryParams 问题查询参数对象，包含各种查询条件
     * @return 返回一个包含问题列表的ApiResponse对象
     */
    ApiResponse<List<QuestionVO>> getQuestions(QuestionQueryParam queryParams);

    /**
     * 创建问题接口
     * 该方法用于提交一个新的问题，以便在系统中创建问题记录
     *
     * @param createQuestionBody 包含要创建的问题的所有必要信息的请求体
     * @return 返回一个包含创建问题结果的ApiResponse对象，包括新创建问题的 ID
     */
    ApiResponse<CreateQuestionVO> createQuestion(CreateQuestionBody createQuestionBody);

    /**
     * 更新问题信息
     * 该方法通过提供的问题 ID 和更新内容来修改现有问题的信息
     *
     * @param questionId 问题的唯一标识符，用于定位哪个问题需要被更新
     * @param updateQuestionBody 包含了需要更新的问题信息的对象
     * @return 返回一个ApiResponse对象
     */
    ApiResponse<EmptyVO> updateQuestion(Integer questionId, UpdateQuestionBody updateQuestionBody);

    /**
     * 删除问题
     * 该方法通过提供的问题 ID 来删除问题记录
     *
     * @param questionId 问题的唯一标识符，用于定位要删除的问题
     * @return 返回一个ApiResponse对象
     */
    ApiResponse<EmptyVO> deleteQuestion(Integer questionId);

    /**
     * 用户获取问题列表
     *
     * @param queryParams 问题查询参数对象，包含各种查询条件如用户 ID
     * @return 返回一个携带用户相关信息的题目列表的 ApiResponse 对象
     */
    ApiResponse<List<QuestionUserVO>> userGetQuestions(QuestionQueryParam queryParams);

    /**
     * 用户获取单个问题
     *
     * @param questionId 问题的唯一标识符，用于定位要获取的问题
     * @return 返回一个携带用户相关信息的题目的 ApiResponse 对象
     */
    ApiResponse<QuestionNoteVO> userGetQuestion(Integer questionId);

    /**
     * 搜索问题
     *
     * @param body 包含搜索问题的请求体
     * @return 返回一个携带搜索结果的 ApiResponse 对象
     */
    ApiResponse<List<QuestionVO>> searchQuestions(SearchQuestionBody body);
}
