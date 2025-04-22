package com.kama.notes.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName MyBatisConfig
 * @Description MyBatis 配置类
 * @Author Tong
 * @LastChangeDate 2024-12-17 16:22
 * @Version v1.0
 */
@Configuration// 修改为正确的Mapper包路径
@MapperScan("com.kama.notes.mapper")
@EnableTransactionManagement
public class MyBatisConfig {
}
