package com.kama.notes;

import com.kama.notes.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName NotesApplication
 * @Description 测试类
 * @Author Tong
 * @LastChangeDate 2024-12-16 14:21
 * @Version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NotesApplication.class)
public class NotesApplicationTests {
    @Autowired
    RedisService redisService;

    @Test
    public void Test() {
        redisService.set("testKey", "testValue");
    }

    @Test
    public void Test2() {
        Object o = redisService.get("testKey");
    }

    @Test
    public void Test3() {
        Object o = redisService.get("random");
        System.out.println(o);
    }
}
