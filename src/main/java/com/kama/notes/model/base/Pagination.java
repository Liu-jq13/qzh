package com.kama.notes.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {
    private Integer page;  // 当前页码
    private Integer pageSize;  // 每页显示的记录数
    private Integer total;  // 总记录数
}
