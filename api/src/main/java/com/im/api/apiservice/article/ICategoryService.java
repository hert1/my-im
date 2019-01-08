package com.im.api.apiservice.article;

import com.im.api.dto.article.CategoryBean;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
public interface ICategoryService {

    public List<CategoryBean> getCategoryByArticleCategoryId(String categoryId);

}
