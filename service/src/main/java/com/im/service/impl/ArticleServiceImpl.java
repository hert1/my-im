package com.im.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.dto.article.*;
import com.im.api.util.UUID;
import com.im.service.dao.ArticleDao;
import com.im.service.dao.ContentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    ArticleDao articleDao;
    @Autowired
    ContentDao contentDao;

    @Override
    public List<ArticleBean> getArticleByNumAndSize(BaseArticleBean articleList) {
        List<ArticleBean> contentsByNumAndSize = null;
        PageHelper.startPage(articleList.getPage(), articleList.getPageSize());
        if ("category".equals(articleList.getBy())) {
            contentsByNumAndSize = contentDao.getContentsByCateory(articleList.getStatus(), articleList.getCategoryId());
        } else if ("tag".equals(articleList.getBy())) {
            List<String> articleIdByTag = contentDao.getArticleIdByTag(articleList.getTagId());
            contentsByNumAndSize = contentDao.getContents(articleIdByTag);
        } else {
            contentsByNumAndSize = contentDao.getContentsByNumAndSize(articleList.getStatus());//0正常发布
        }
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
        contentDao.modifyCategory(categoryId, categoryName);
    }

    @Override
    public void modifyTag(String tagId, String tagName) {
        contentDao.modifyTag(tagId, tagName);
    }

    @Override
    public void publArticle(ArticleBean articleBean) {
        contentDao.publArticle(articleBean);
    }

    @Override
    public void saveArticle(ArticleBean articleBean) {
        contentDao.saveArticle(articleBean);
    }

    @Override
    public void modifyArticle(ArticleBean articleBean) {
        contentDao.modifyArticle(articleBean);
    }

    @Override
    public void deleteArticle(String id) {
        ArticleBean contentsByNum = contentDao.getContentsByNum(id);
        if (contentsByNum != null) {
            if (contentsByNum.getStatus() == 1) {
                contentDao.deleteArticle(id);
            } else {
                contentDao.removeArticle(id);
            }
        }
        //  contentDao.modifyArticle(articleBean);
    }

    @Override
    public void bindArticleAndTag(String aid, String tid) {
        contentDao.bindArticleAndTag(aid, tid, new Date());
    }

    @Override
    public CategoryBean getCategory(String categoryId) {
        return contentDao.getCategory(categoryId);
    }

    @Override
    public Tag getTag(String tagId) {
        return contentDao.getTag(tagId);
    }

    @Override
    public List<Tag> getTagByArticleId(String articleId) {
        return contentDao.getTagByArticleId(articleId);
    }

    @Override
    public List<FriendsBean> getFriendsList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);

        return contentDao.getFriendsList();
    }

    @Override
    public List<FriendTypeList> getFriendTypeList() {
        return contentDao.getFriendTypeList();
    }

    @Override
    public void addFriends(String name, String url, int typeId) {
        FriendsBean friendsBean = new FriendsBean();
        friendsBean.setFriend_id(UUID.UU64());
        friendsBean.setName(name);
        friendsBean.setCreate_time(new Date());
        friendsBean.setType_id(typeId);
        friendsBean.setUrl(url);
        contentDao.addFriends(friendsBean);
    }

    @Override
    public void deleteFriend(String fid) {
        contentDao.deleteFriend(fid);
    }
}
