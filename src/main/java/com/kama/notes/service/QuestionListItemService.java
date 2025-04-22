package com.kama.notes.service;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.dto.questionListItem.CreateQuestionListItemBody;
import com.kama.notes.model.dto.questionListItem.QuestionListItemQueryParams;
import com.kama.notes.model.dto.questionListItem.SortQuestionListItemBody;
import com.kama.notes.model.entity.QuestionListItem;
import com.kama.notes.model.vo.questionListItem.CreateQuestionListItemVO;
import com.kama.notes.model.vo.questionListItem.QuestionListItemUserVO;
import com.kama.notes.model.vo.questionListItem.QuestionListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface QuestionListItemService {

    /**
     * 获取题单项（用户端）
     *
     * @param queryParams 查询参数
     * @return 返回一个包含题单项的ApiResponse对象
     */
    ApiResponse<List<QuestionListItemUserVO>> userGetQuestionListItems(QuestionListItemQueryParams queryParams);

    /**
     * 获取题单项（管理端）
     *
     * @param questionListId 题单的ID，用于指定获取哪个题单的项
     * @return 返回一个包含题单项的ApiResponse对象
     */
    ApiResponse<List<QuestionListItemVO>> getQuestionListItems(Integer questionListId);

    /**
     * 创建题单项
     *
     * @param body 包含创建题单项所需信息的请求体
     * @return 返回一个包含创建题单项结果的ApiResponse对象
     */
    ApiResponse<CreateQuestionListItemVO> createQuestionListItem(CreateQuestionListItemBody body);

    /**
     * 删除题单项
     *
     * @param questionListId 题单的ID，用于指定从哪个题单中删除项
     * @param questionId     要删除的问题的ID
     * @return 返回一个表示删除操作结果的ApiResponse对象
     */
    ApiResponse<EmptyVO> deleteQuestionListItem(Integer questionListId, Integer questionId);

    /**
     * 对题单项进行排序
     *
     * @param body 包含排序信息，包括题单ID和题单项ID列表
     * @return 返回一个表示排序操作结果的ApiResponse对象
     */
    ApiResponse<EmptyVO> sortQuestionListItem(SortQuestionListItemBody body);
}
