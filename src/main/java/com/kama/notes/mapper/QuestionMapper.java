package com.kama.notes.mapper;

import com.kama.notes.model.dto.question.QuestionQueryParam;
import com.kama.notes.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    /**
     * 插入一个问题对象到数据库中
     *
     * @param question 要插入的问题对象，包含问题的相关信息
     * @return 插入成功返回1，否则返回0
     */
    int insert(Question question);

    /**
     * 根据问题ID查找问题
     *
     * @param questionId 问题的唯一标识符
     * @return 返回找到的问题对象，如果没有找到则返回null
     */
    Question findById(@Param("questionId") Integer questionId);

    /**
     * 批量通过问题  ID查找问题
     * 此方法允许一次性传入多个问题ID，从而批量获取问题信息
     *
     * @param questionIds 一个问题ID的列表，用于指定需要查找的问题
     * @return 返回一个Question对象的列表，每个对象包含一个问题的详细信息
     */
    List<Question> findByIdBatch(@Param("questionIds") List<Integer> questionIds);

    /**
     * 根据查询参数获取问题列表
     *
     * @param queryParam 查询参数对象，包含多种可能的查询条件
     * @param offset 分页查询的起始位置
     * @param limit 每页返回的最大记录数
     * @return 匹配查询条件的问题列表
     */
    List<Question> findByQueryParam(@Param("queryParam") QuestionQueryParam queryParam,
                                    @Param("offset") int offset,
                                    @Param("limit") int limit);

    /**
     * 根据关键字搜索问题
     *
     * @param keyword 关键字，用于匹配问题标题或内容
     * @return 匹配关键字的问题列表
     */
    List<Question> findByKeyword(@Param("keyword") String keyword);

    /**
     * 更新问题
     * @param question 问题对象，包含要更新的问题信息
     * @return 更新成功返回1，否则返回0
     */
    int update(@Param("question") Question question);

    /**
     * 更新问题的浏览次数
     * @param questionId 问题ID
     * @return 更新成功返回1，否则返回0
     */
    int incrementViewCount(@Param("questionId") Integer questionId);

    /**
     * 根据查询参数统计问题的数量
     *
     * @param queryParam 查询参数对象，包含多个查询条件
     * @return 满足查询条件的问题数量
     */
    int countByQueryParam(@Param("queryParam") QuestionQueryParam queryParam);

    /**
     * 根据问题ID删除问题
     *
     * @param questionId 需要删除的问题的ID
     */
    int deleteById(Integer questionId);

    /**
     * 根据分类ID删除相关记录
     * 此方法旨在删除与给定分类ID 关联的实体
     *
     * @param categoryId 分类ID，用于标识要删除的记录
     */
    int deleteByCategoryId(Integer categoryId);

    /**
     * 批量删除指定分类ID的实体
     * 通过分类ID列表来删除实体，主要用于批量操作场景
     *
     * @param categoryIds 分类ID列表，用于指定待删除实体的分类
     */
    int deleteByCategoryIdBatch(@Param("categoryIds") List<Integer> categoryIds);
}
