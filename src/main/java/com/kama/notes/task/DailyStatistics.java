package com.kama.notes.task;

import com.kama.notes.mapper.StatisticMapper;
import com.kama.notes.mapper.NoteMapper;
import com.kama.notes.mapper.UserMapper;
import com.kama.notes.model.entity.Statistic;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Log4j2
@Component
public class DailyStatistics {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    StatisticMapper statisticMapper;

    /**
     * 统计日常提交笔记、登录数据
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void dailyStatistics() {

        Statistic statistic = new Statistic();
        /**
         * 获取统计数据
         */
        // 用户
        int todayLoginCount = userMapper.getTodayLoginCount();
        int todayRegisterCount = userMapper.getTodayRegisterCount();
        int totalRegisterCount = userMapper.getTotalRegisterCount();

        // 笔记
        int todayNoteCount = noteMapper.getTodayNoteCount();
        int todaySubmitNoteUserCount = noteMapper.getTodaySubmitNoteUserCount();
        int totalNoteCount = noteMapper.getTotalNoteCount();

        /**
         * 设置统计数据
         */
        statistic.setLoginCount(todayLoginCount);
        statistic.setRegisterCount(todayRegisterCount);
        statistic.setTotalRegisterCount(totalRegisterCount);

        statistic.setNoteCount(todayNoteCount);
        statistic.setSubmitNoteCount(todaySubmitNoteUserCount);
        statistic.setTotalNoteCount(totalNoteCount);

        statistic.setDate(LocalDate.now());

        try {
            statisticMapper.insert(statistic);
            log.info("[定时任务]统计每日数据, 插入数据成功, statistic={}", statistic);
        } catch (Exception e) {
            log.error("[定时任务]统计每日数据, 插入数据失败, statistic={}, 错误详情={}", statistic, e.getMessage());
            e.printStackTrace();
        }
    }
}
