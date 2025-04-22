package com.kama.notes.mapper;

import com.kama.notes.model.entity.QuestionList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionListMapper {
    /**
     * 插入一个题单
     *
     * @param questionList 要插入的题单对象
     * @return 插入操作影响的行数
     */
    int insert(QuestionList questionList);

    /**
     * 根据题单ID查找题单
     *
     * @param questionListId 题单的唯一标识符
     * @return 返回找到的题单对象，如果没有找到则返回 null
     */
    QuestionList findById(@Param("questionListId") Integer questionListId);

    /**
     * 获取所有题单
     *
     * @return 返回所有题单的列表
     */
    List<QuestionList> findAll();

    /**
     * 更新一个题单的信息
     *
     * @param questionList 要更新的题单对象，包含需要更新的字段
     * @return 更新操作影响的行数
     */
    int update(QuestionList questionList);

    /**
     * 根据题单ID删除题单
     *
     * @param questionListId 题单的唯一标识符
     * @return 删除操作影响的行数
     */
    int deleteById(@Param("questionListId") Integer questionListId);
}
