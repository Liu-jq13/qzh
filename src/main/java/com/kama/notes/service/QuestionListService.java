package com.kama.notes.service;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.dto.questionList.CreateQuestionListBody;
import com.kama.notes.model.dto.questionList.UpdateQuestionListBody;
import com.kama.notes.model.entity.QuestionList;
import com.kama.notes.model.vo.questionList.CreateQuestionListVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface QuestionListService {
    /**
     * 获取题单
     *
     * @return ApiResponse 包含题单的响应对象
     */
    ApiResponse<QuestionList> getQuestionList(Integer questionListId);

    /**
     * 获取题单列表
     *
     * @return ApiResponse 包含题单的响应对象
     */
    ApiResponse<List<QuestionList>> getQuestionLists();

    /**
     * 创建新的题单
     *
     * @param body 包含创建题单所需信息的请求体
     * @return ApiResponse 包含新创建的题单信息的响应对象
     */
    ApiResponse<CreateQuestionListVO> createQuestionList(CreateQuestionListBody body);

    /**
     * 删除题单
     *
     * @param questionListId 要删除的题单的ID
     * @return ApiResponse 表示删除操作结果的响应对象
     */
    ApiResponse<EmptyVO> deleteQuestionList(Integer questionListId);

    /**
     * 更新题单信息
     *
     * @param questionListId 要更新的题单的ID
     * @param body 包含要更新的题单信息的请求体
     * @return ApiResponse 表示更新操作结果的响应对象
     */
    ApiResponse<EmptyVO> updateQuestionList(Integer questionListId, UpdateQuestionListBody body);
}
