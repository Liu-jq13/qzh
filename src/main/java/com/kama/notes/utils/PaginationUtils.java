package com.kama.notes.utils;

public class PaginationUtils {
    /**
     * 根据页码和每页大小计算偏移量
     *
     * @param page     当前页码，从 1 开始
     * @param pageSize 每页大小，必须大于 0
     * @return 计算出的偏移量
     * @throws IllegalArgumentException 如果 page 小于 1 或 pageSize 小于 1
     */
    public static int calculateOffset(int page, int pageSize) {
        if (page < 1) {
            throw new IllegalArgumentException("页码 (page) 必须大于或等于 1");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("每页大小 (pageSize) 必须大于 0");
        }
        return (page - 1) * pageSize;
    }
}
