package com.qzh.notes.service.impl;

import com.qzh.notes.mapper.StatisticMapper;
import com.qzh.notes.model.base.ApiResponse;
import com.qzh.notes.model.base.Pagination;
import com.qzh.notes.model.dto.statistic.StatisticQueryParam;
import com.qzh.notes.model.entity.Statistic;
import com.qzh.notes.service.StatisticService;
import com.qzh.notes.utils.ApiResponseUtil;
import com.qzh.notes.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public ApiResponse<List<Statistic>> getStatistic(StatisticQueryParam queryParam) {

        Integer page = queryParam.getPage();
        Integer pageSize = queryParam.getPageSize();
        int offset = PaginationUtils.calculateOffset(page, pageSize);
        int total = statisticMapper.countStatistic();

        Pagination pagination = new Pagination(page, pageSize, total);

        try {
            List<Statistic> statistics = statisticMapper.findByPage(pageSize, offset);
            return ApiResponseUtil.success("获取统计列表成功", statistics, pagination);
        } catch (Exception e) {
            return ApiResponseUtil.error(e.getMessage());
        }
    }
}
