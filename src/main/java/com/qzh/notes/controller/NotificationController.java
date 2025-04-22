package com.qzh.notes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qzh.notes.model.base.ApiResponse;
import com.qzh.notes.model.base.EmptyVO;
import com.qzh.notes.model.dto.notification.NotificationDTO;
import com.qzh.notes.model.vo.notification.NotificationVO;
import com.qzh.notes.service.RedisService;
import com.qzh.notes.utils.ApiResponseUtil;

@RestController
@RequestMapping("/api")
public class NotificationController {

    // 由于比较简单，直接全写在 controller 内了，免得出现透传
    @Autowired
    private RedisService redisService;

    @GetMapping("/notification")
    public ApiResponse<NotificationVO> getNotifications() {
        NotificationVO notificationVO = new NotificationVO();
        Object o = redisService.get("kamanote:notification");
        String content = o == null ? "" : o.toString();
        notificationVO.setContent(content);
        return ApiResponseUtil.success("获取通知成功", notificationVO);
    }

    @PostMapping("/notification")
    public ApiResponse<EmptyVO> setNotifications(@Valid @RequestBody NotificationDTO notificationDTO) {
        redisService.set("kamanote:notification", notificationDTO.getContent());
        return ApiResponseUtil.success("设置通知成功");
    }
}
