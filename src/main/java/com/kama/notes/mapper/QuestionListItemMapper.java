package com.kama.notes.mapper;

import com.kama.notes.model.entity.QuestionListItem;
import com.kama.notes.model.vo.questionListItem.QuestionListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionListItemMapper {
    /**
     * 插入一个题单项
     *
     * @param questionListItem 题单项对象，包含需要插入的题单项的信息
     * @return 影响的行数，表示插入操作是否成功
     */
    int insert(QuestionListItem questionListItem);

    /**
     * 根据题单ID查找题单项
     *
     * @param questionListId 题单的唯一标识符
     * @return 返回一个包含题单项的列表如果找不到对应的项，则返回空列表
     */
    List<QuestionListItemVO> findByQuestionListId(@Param("questionListId") Integer questionListId);

    /**
     * 根据题单ID查找题单项的数量
     *
     * @param questionListId 题单的ID，用于标识要查找的题单项数量
     * @return 返回题单项的数量
     */
    int countByQuestionListId(@Param("questionListId") Integer questionListId);

    /**
     * 根据题单ID查找题单项（分页）
     *
     * @param questionListId 题单的ID，用于标识要查找的题单项
     * @param limit          每页显示的记录数
     * @param offset         从第几条记录开始查询
     */
    List<QuestionListItemVO> findByQuestionListIdPage(@Param("questionListId") Integer questionListId,
                                                      @Param("limit") Integer limit,
                                                      @Param("offset") Integer offset);

    /**
     * 根据题单ID删除题单项
     *
     * @param questionListId 题单的ID，用于标识要删除的题单项
     * @return 影响的行数，表示删除操作是否成功
     */
    int deleteByQuestionListId(Integer questionListId);

    /**
     * 根据题单ID和题目ID删除题单项
     *
     * @param questionListId 题单的ID，用于标识要删除的题单项
     * @param questionId     题目的ID，用于标识要删除的题单项
     * @return 影响的行数，表示删除操作是否成功
     */
    int deleteByQuestionListIdAndQuestionId(@Param("questionListId") Integer questionListId, @Param("questionId") Integer questionId);

    /**
     * 根据题单ID获取下一个序号
     *
     * @param questionListId 题单的ID
     * @return 返回下一个序号
     */
    int nextRank(Integer questionListId);

    /**
     * 更新题单项的序号
     *
     * @param questionListItem 新顺序的题单项
     * @return 影响的行数，表示更新操作是否成功
     */
    int updateQuestionRank(QuestionListItem questionListItem);
}
