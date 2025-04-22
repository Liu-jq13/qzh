package com.kama.notes.mapper;

import com.kama.notes.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 此方法负责接收一个Category对象，并执行插入操作，具体操作如将分类信息保存到数据库
     * 主要用于添加新的分类信息
     *
     * @param category 要插入的分类对象，包含分类相关信息
     * @return 返回插入操作的结果，表示插入的记录数
     */
    int insert(Category category);

    /**
     * 批量插入分类数据
     *
     * @param categories 分类对象列表，包含多个Category实例
     * @return 插入操作影响的行数
     */
    int insertBatch(@Param("categories") List<Category> categories);

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    List<Category> categoryList();

    /**
     * 根据分类ID查找分类信息
     *
     * @param categoryId 分类ID，用于唯一标识一个分类
     * @return 返回找到的Category对象，如果不存在则返回null
     */
    Category findById(Integer categoryId);

    /**
     * 批量通过分类ID查找分类信息
     * 此方法允许一次性查询多个分类的信息，通过提供一个分类ID列表作为参数
     *
     * @param categoryIds 分类ID列表，用于指定需要查找的分类
     * @return 匹配给定ID的分类对象列表如果没有找到匹配的分类，则返回空列表
     */
    List<Category> findByIdBatch(@Param("categoryIds") List<Integer> categoryIds);

    /**
     * 根据分类ID或父分类ID查找分类信息
     * 此方法旨在处理分类信息的查询，通过分类ID或父分类ID来过滤并返回匹配的分类对象列表
     *
     * @param categoryId 分类ID或父分类ID，用于查找分类的依据
     * @return 返回一个Category对象的列表，这些对象的分类ID或父分类ID与给定的categoryId匹配
     */
    List<Category> findByIdOrParentId(Integer categoryId);

    /**
     * 删除分类以及子分类
     *
     * @param categoryId 分类 ID
     */
    int deleteById(Integer categoryId);

    /**
     * 批量删除分类
     * 通过多个分类ID一次性删除对应的分类信息
     *
     * @param categoryIds 包含多个分类ID的列表，用于指定需要删除的分类
     * @return 返回删除操作的影响行数，表示成功删除的分类数量
     */
    int deleteByIdBatch(@Param("categoryIds") List<Integer> categoryIds);

    /**
     * 更新分类信息
     *
     * @param category 要更新的分类对象，包含新的分类信息
     * @return 返回更新操作的影响行数，表示成功更新的分类数量
     */
    int update(Category category);
}
