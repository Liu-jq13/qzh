package com.kama.notes.model.base;

import lombok.Data;
import java.util.List;

/**
 * 分页数据模型
 *
 * @param <T> 数据类型
 */
@Data
public class PageVO<T> {
    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 数据列表
     */
    private List<T> list;

    public PageVO(Integer page, Integer pageSize, Integer total, List<T> list) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    /**
     * 创建分页结果
     *
     * @param list 数据列表
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param total 总记录数
     * @return 分页结果
     */
    public static <T> PageVO<T> of(List<T> list, Integer page, Integer pageSize, Integer total) {
        PageVO<T> pageVO = new PageVO<>(page, pageSize, total, list);
        pageVO.setTotalPages((total + pageSize - 1) / pageSize);
        return pageVO;
    }
} 