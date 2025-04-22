package com.kama.notes.mapper;

import com.kama.notes.model.dto.user.UserQueryParam;
import com.kama.notes.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper接口定义了用户数据访问对象（DAO）的方法
 */
@Mapper
public interface UserMapper {
    /**
     * 插入新用户
     *
     * @param user 待插入的用户对象，包含用户的所有信息
     */
    int insert(User user);

    /**
     * 根据ID查找用户
     *
     * @param userId 用户ID，用于查询用户信息
     * @return 返回用户对象，如果未找到则返回null
     */
    User findById(@Param("userId") Long userId);

    /**
     * 根据 ID 数组批量查找用户
     *
     * @param userIds 用户ID列表，用于批量查询用户信息
     * @return 返回用户列表，如果未找到任何用户则返回空列表
     */
    List<User> findByIdBatch(@Param("userIds") List<Long> userIds);

    /**
     * 根据账号查找用户
     *
     * @param account 用户账号，用于查询用户信息
     * @return 返回用户对象，如果未找到则返回null
     */
    User findByAccount(@Param("account") String account);

    /**
     * 根据 OpenId 查找用户
     *
     * @param openId 用户的 OpenId，用于查询用户信息
     * @return 返回用户对象，如果未找到则返回 null
     */
    User findByOpenId(@Param("openId") String openId);

    /**
     * 根据 UnionId 查找用户
     *
     * @param unionId 用户的 UnionId，用于查询用户信息
     * @return 返回用户对象，如果未找到则返回 null
     */
    User findByUnionId(@Param("unionId") String unionId);

    /**
     * 根据查询参数查找用户列表
     *
     * @param queryParams 用户查询参数对象，封装了查询用户时的各种筛选条件
     * @return 符合查询条件的用户列表
     */
    List<User> findByQueryParam(@Param("queryParams") UserQueryParam queryParams,
                                @Param("limit") Integer limit,
                                @Param("offset") Integer offset);

    /**
     * 根据查询参数统计用户数量
     *
     * @param queryParams 用户查询参数对象，封装了查询条件
     * @return 满足查询条件的用户数量
     */
    int countByQueryParam(@Param("queryParams") UserQueryParam queryParams);

    /**
     * 更新用户信息
     *
     * @param user 待更新的用户对象，包含用户的所有信息
     */
    int update(User user);

    /**
     * 更新用户在线时间
     *
     * @param userId 用户ID，用于标识需要更新在线时间的用户
     */
    int updateLastLoginAt(@Param("userId") Long userId);

    /**
     * 绑定手机号
     * @param userId 用户ID
     * @param phone 手机号码
     * @return 绑定结果
     */
    int bindPhone(@Param("userId") Long userId, @Param("phone") String phone);

    /**
     * 获取今日登录人数
     * @return 今日登录人数
     */
    int getTodayLoginCount();

    /**
     * 今日注册人数
     * @return 今日注册人数
     */
    int getTodayRegisterCount();

    /**
     * 总注册人数
     * @return 总注册人数
     */
    int getTotalRegisterCount();

    /**
     * 根据邮箱查找用户
     *
     * @param email 用户邮箱，用于查询用户信息
     * @return 返回用户对象，如果未找到则返回null
     */
    User findByEmail(@Param("email") String email);

    /**
     * 搜索用户
     *
     * @param keyword 关键词
     * @param limit 限制数量
     * @param offset 偏移量
     * @return 用户列表
     */
    List<User> searchUsers(@Param("keyword") String keyword,
                          @Param("limit") int limit,
                          @Param("offset") int offset);
}
