package com.kama.notes.service.impl;

import com.kama.notes.mapper.EmailVerifyCodeMapper;
import com.kama.notes.model.entity.EmailVerifyCode;
import com.kama.notes.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerifyCodeMapper emailVerifyCodeMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${mail.verify-code.expire-minutes}")
    private int expireMinutes;

    @Value("${mail.verify-code.resend-interval}")
    private int resendInterval;

    @Override
    public String sendVerifyCode(String email, String type) {
        // 检查发送频率
        if (!canSendCode(email)) {
            throw new RuntimeException("发送太频繁，请稍后再试");
        }

        // 生成6位随机验证码
        String verifyCode = generateVerifyCode();

        try {
            // 发送邮件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("卡码笔记 - 验证码");
            message.setText("您的验证码是：" + verifyCode + "，有效期" + expireMinutes + "分钟，请勿泄露给他人。");
            mailSender.send(message);

            // 保存验证码记录
            EmailVerifyCode code = new EmailVerifyCode();
            code.setEmail(email);
            code.setCode(verifyCode);
            code.setType(type);
            code.setExpiredAt(LocalDateTime.now().plusMinutes(expireMinutes));
            emailVerifyCodeMapper.insert(code);

            // 记录发送时间到Redis
            String redisKey = "email:verify:limit:" + email;
            redisTemplate.opsForValue().set(redisKey, "1", resendInterval, TimeUnit.SECONDS);

            return verifyCode;
        } catch (Exception e) {
            log.error("发送验证码邮件失败", e);
            throw new RuntimeException("发送验证码失败，请稍后重试");
        }
    }

    @Override
    public boolean verifyCode(String email, String code, String type) {
        // 查询最新的未使用的验证码
        EmailVerifyCode verifyCode = emailVerifyCodeMapper.findLatestValidCode(email, type);

        if (verifyCode == null) {
            return false;
        }

        // 检查是否过期
        if (verifyCode.getExpiredAt().isBefore(LocalDateTime.now())) {
            return false;
        }

        // 检查验证码是否正确
        if (!verifyCode.getCode().equals(code)) {
            return false;
        }

        // 标记验证码为已使用
        emailVerifyCodeMapper.markAsUsed(verifyCode.getId());

        return true;
    }

    @Override
    public boolean canSendCode(String email) {
        String redisKey = "email:verify:limit:" + email;
        return redisTemplate.opsForValue().get(redisKey) == null;
    }

    private String generateVerifyCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
} 