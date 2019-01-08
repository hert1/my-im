package com.im.service.dao;

import com.im.api.dto.article.CategoryBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Mapper
public interface CategoryDao {

    @Select("select * from category where id=#{categoryId}")
    public List<CategoryBean> getCategoryByArticleCategoryId(String categoryId);

}
