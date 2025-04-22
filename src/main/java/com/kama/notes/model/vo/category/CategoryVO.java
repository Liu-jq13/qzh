package com.kama.notes.model.vo.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {
    private Integer categoryId;
    private String name;
    private Integer parentCategoryId;
    private List<ChildrenCategoryVO> children;

    @Data
    public static class ChildrenCategoryVO {
        private Integer categoryId;
        private String name;
        private Integer parentCategoryId;
    }
}
