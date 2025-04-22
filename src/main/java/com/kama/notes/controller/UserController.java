package com.kama.notes.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.dto.user.LoginRequest;
import com.kama.notes.model.dto.user.RegisterRequest;
import com.kama.notes.model.dto.user.UpdateUserRequest;
import com.kama.notes.model.dto.user.UserQueryParam;
import com.kama.notes.model.entity.User;
import com.kama.notes.model.vo.user.AvatarVO;
import com.kama.notes.model.vo.user.LoginUserVO;
import com.kama.notes.model.vo.user.RegisterVO;
import com.kama.notes.model.vo.user.UserVO;
import com.kama.notes.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    // 自动注入UserService以使用用户相关服务
    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * 处理用户注册请求，验证请求体并调用 userService 进行注册
     *
     * @param request 用户注册请求对象，包含用户注册所需信息
     * @return 返回注册结果，包括用户信息等
     */
    @PostMapping("/users")
    public ApiResponse<RegisterVO> register(
            @Valid
            @RequestBody
            RegisterRequest request) {
        return userService.register(request);
    }

    /**
     * 用户登录接口
     * 处理用户登录请求，验证请求体并调用userService进行登录
     *
     * @param request 用户登录请求对象，包含用户登录所需信息
     * @return 返回登录结果，包括用户信息和认证令牌等
     */
    @PostMapping("/users/login")
    public ApiResponse<LoginUserVO> login(
            @Valid
            @RequestBody
            LoginRequest request) {
        return userService.login(request);
    }

    /**
     * 自动登录接口
     * 当用户已登录并请求自动登录时，调用userService获取当前用户信息
     *
     * @return 返回当前用户信息
     */
    @PostMapping("/users/whoami")
    public ApiResponse<LoginUserVO> whoami() {
        return userService.whoami();
    }

    /**
     * 查询用户信息接口
     * 根据用户ID查询用户信息，验证ID格式并调用userService获取用户详情
     *
     * @param userId 用户ID，需为数字格式
     * @return 返回指定用户的详细信息
     */
    @GetMapping("/users/{userId}")
    public ApiResponse<UserVO> getUserInfo(
            @PathVariable
            @Pattern(regexp = "\\d+", message = "ID 格式错误")
            Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 更新用户信息接口
     * 处理更新用户信息请求，验证请求体并调用userService更新用户详情
     *
     * @param request 更新用户请求对象，包含需要更新的用户信息
     * @return 返回更新后的用户信息
     */
    @PatchMapping("/users/me")
    public ApiResponse<LoginUserVO> updateUserInfo(
            @Valid
            @RequestBody
            UpdateUserRequest request) {
        return userService.updateUserInfo(request);
    }

    /**
     * 上传用户头像接口
     *
     * @param file 头像文件
     * @return 返回上传结果，包括头像URL等
     */
    @PostMapping("/users/avatar")
    public ApiResponse<AvatarVO> uploadAvatar(
            @RequestParam("file") MultipartFile file) {
        return userService.uploadAvatar(file);
    }

    /**
     * 管理员获取用户信息列表的接口
     * 该接口允许管理员查询系统的用户列表，支持分页和条件查询
     *
     * @param queryParam 查询参数对象，封装了用户查询条件和分页信息，通过验证确保参数有效性
     * @return 返回一个包含用户列表的ApiResponse对象，响应中包含用户数据
     */
    @GetMapping("/admin/users")
    public ApiResponse<List<User>> adminGetUser(
            @Valid UserQueryParam queryParam) {
        return userService.getUserList(queryParam);
    }
}
