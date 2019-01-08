package com.im.service.impl;

import com.github.pagehelper.PageHelper;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.BaseResponse;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;
import com.im.service.dao.ContentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
@Component
@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    ContentDao contentDao;
    @Override
    public List<ArticleBean> getArticleByNumAndSize(int num, int size,int status) {

        PageHelper.startPage(num,size);
        List<ArticleBean> contentsByNumAndSize = contentDao.getContentsByNumAndSize(status);//0正常发布
        return contentsByNumAndSize;
    }

    @Override
    public Integer getArticleNum(int status) {
        return contentDao.getContentsNum(status);
    }

    @Override
    public Integer getCategoryCount() {
        int categoryNum = contentDao.getCategoryNum();
        return categoryNum;
    }

    @Override
    public Integer getTagCount() {
        int tagNum = contentDao.getTagNum();
        return tagNum;
    }

    @Override
    public Integer getCommentsCount() {
        return contentDao.getCommentsCount();
    }

    @Override
    public void addCategory(CategoryBean category) {
        category.setCreate_time(new java.util.Date());
        contentDao.addCategory(category);
    }

    @Override
    public void addTag(Tag tag) {
        tag.setCreate_time(new java.util.Date());
        contentDao.addTag(tag);
    }

    @Override
    public List<Tag> getTagList() {
        return contentDao.getTagList();
    }

    @Override
    public List<CategoryBean> getCategoryList() {
        return contentDao.getCategoryList();
    }

    @Override
    public void deleteTag(String tagId) {
        contentDao.deleteTag(tagId);
        contentDao.deleteArticleAndTag(tagId);
    }

    @Override
    public void deleteCategory(String categoryId) {
        contentDao.deleteCategory(categoryId);
        contentDao.deleteArticleAndCateory(categoryId);
    }

    @Override
    public void modifyCategory(String categoryId, String categoryName) {
        contentDao.modifyCategory(categoryId,categoryName);
    }

    @Override
    public void modifyTag(String tagId, String tagName) {
        contentDao.modifyTag(tagId,tagName);
    }

    @Override
    public void publArticle(ArticleBean articleBean) {
        contentDao.publArticle(articleBean);
    }

    @Override
    public void bindArticleAndTag(String aid, String tid) {
        contentDao.bindArticleAndTag(aid,tid,new Date());
    }


}
