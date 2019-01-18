package com.im.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.im.api.apiservice.article.ICategoryService;
import com.im.api.dto.article.CategoryBean;
import com.im.service.dao.CategoryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Component
@Slf4j
@Service(version = "1.0",timeout = 3000)
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryDao categoryDao;
    @Override
    public List<CategoryBean> getCategoryByArticleCategoryId(String categoryId) {

        return categoryDao.getCategoryByArticleCategoryId(categoryId);
    }
}
