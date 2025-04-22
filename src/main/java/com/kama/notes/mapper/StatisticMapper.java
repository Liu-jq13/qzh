package com.kama.notes.mapper;

import com.kama.notes.model.entity.Statistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StatisticMapper {
    /**
     * 添加统计数据
     * @param statistic 统计数据
     * @return 添加的记录数
     */
    int insert(Statistic statistic);

    /**
     * 获取统计数据的 total
     * @return total
     */
    int countStatistic();

    /**
     * 查询统计数据
     * @param limit 限制
     * @param offset 偏移
     * @return 统计数据
     */
    List<Statistic> findByPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
}
