package com.kama.notes.service;

/**
 * Redis服务接口，定义了对Redis数据库的基本操作
 */
public interface RedisService {
    /**
     * 保存数据到Redis
     *
     * @param key 数据的键
     * @param value 数据的值
     */
    void set(String key, Object value);

    /**
     * 保存数据到Redis并设置过期时间
     *
     * @param key 数据的键
     * @param value 数据的值
     * @param timeout 数据的过期时间，单位秒
     */
    void setWithExpiry(String key, Object value, long timeout);

    /**
     * 从Redis获取数据
     *
     * @param key 数据的键
     * @return 数据的值，如果键不存在则返回null
     */
    Object get(String key);

    /**
     * 从Redis删除数据
     *
     * @param key 数据的键
     */
    void delete(String key);

    /**
     * 判断Redis中是否存在指定的键
     *
     * @param key 数据的键
     * @return 如果键存在返回true，否则返回false
     */
    boolean exists(String key);

    /**
     * 增加计数
     *
     * @param key 数据的键
     * @param delta 增加的数值
     * @return 增加后的数值
     */
    Long increment(String key, long delta);

    /**
     * 获取Hash类型数据的值
     *
     * @param hashKey Hash的键
     * @param key 数据的键
     * @return 数据的值，如果键不存在则返回null
     */
    Object getHashValue(String hashKey, String key);

    /**
     * 设置Hash类型数据的值
     *
     * @param hashKey Hash的键
     * @param key 数据的键
     * @param value 数据的值
     */
    void setHashValue(String hashKey, String key, Object value);
}
