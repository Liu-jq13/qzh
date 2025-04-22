package com.kama.notes.service;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.dto.user.LoginRequest;
import com.kama.notes.model.dto.user.RegisterRequest;
import com.kama.notes.model.dto.user.UpdateUserRequest;
import com.kama.notes.model.dto.user.UserQueryParam;
import com.kama.notes.model.entity.User;
import com.kama.notes.model.vo.user.AvatarVO;
import com.kama.notes.model.vo.user.RegisterVO;
import com.kama.notes.model.vo.user.LoginUserVO;
import com.kama.notes.model.vo.user.UserVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Transactional
public interface UserService {
    /**
     * 用户注册服务
     *
     * @param request 包含用户账号、密码、邮箱等注册信息的请求对象
     * @return 包含注册成功用户信息的响应对象
     */
    ApiResponse<RegisterVO> register(RegisterRequest request);

    /**
     * 用户登录服务
     *
     * @param request 包含用户账号和密码的登录请求对象
     * @return 包含登录成功后的用户信息（包括 token）的响应对象
     */
    ApiResponse<LoginUserVO> login(LoginRequest request);

    /**
     * 自动登录服务
     *
     * @return 当前登录用户的信息（基于 token 验证的自动登录）
     */
    ApiResponse<LoginUserVO> whoami();

    /**
     * 查询用户信息服务
     *
     * @param userId 需要查询的用户的唯一标识 ID
     * @return 包含用户详细信息的响应对象
     */
    ApiResponse<UserVO> getUserInfo(Long userId);

    /**
     * 更新用户信息服务
     *
     * @param request 包含需要更新的用户信息（如用户名、头像、签名等）的请求对象
     * @return 更新后的用户信息响应对象
     */
    ApiResponse<LoginUserVO> updateUserInfo(UpdateUserRequest request);

    /**
     * 根据用户 ID 列表查询并转换为 Map 格式
     *
     * @param authorIds 包含多个用户 ID 的列表
     * @return 一个 Map，其中键是 userId，值是对应的 User 对象
     */
    Map<Long, User> getUserMapByIds(List<Long> authorIds);

    /**
     * 获取用户列表
     *
     * @param userQueryParam 用户查询参数，包含查询用户列表的条件
     * @return 包含用户列表的 ApiResponse 对象
     */
    ApiResponse<List<User>> getUserList(UserQueryParam userQueryParam);

    /**
     * 上传用户头像
     *
     * @param file 文件对象
     * @return 包含上传成功头像的 URL 的响应对象
     */
    ApiResponse<AvatarVO> uploadAvatar(MultipartFile file);
}
