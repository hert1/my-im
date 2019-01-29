package com.im.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.dto.article.*;
import com.im.api.util.UUID;
import com.im.redis.client.RedisClient;
import com.im.service.config.SolrConfig;
import com.im.service.dao.ArticleDao;
import com.im.service.dao.ContentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
@Component
@Slf4j
@Service(version = "1.0",timeout = 3000)
public class ArticleServiceImpl implements IArticleService {


    @Autowired
    private SolrConfig solrConfig;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    ContentDao contentDao;
    @Autowired
    RedisClient redisClient;
    @Value("${com.im.cache.time}")
    long time;

    @Override
    public PageInfo<ArticleBean> getArticleList(BaseArticleBean articleList) {
        List<ArticleBean> contentsBy = null;
        PageHelper.startPage(articleList.getPage(), articleList.getPageSize());
        if ("category".equals(articleList.getBy())) {
            contentsBy = contentDao.getContentsByCateory(articleList.getStatus(), articleList.getCategoryId());
        } else if ("tag".equals(articleList.getBy())) {
            List<String> articleIdByTag = contentDao.getArticleIdByTag(articleList.getTagId());
            if (articleIdByTag!=null && articleIdByTag.size()>0) {
                contentsBy = contentDao.getContents(articleIdByTag);
            }
        }  else if ("status".equals(articleList.getBy())) {
            contentsBy = contentDao.getNewArticle();
        } else {
            contentsBy = contentDao.getContentsByNumAndSize(articleList.getStatus());//0正常发布
        }
        return new PageInfo<>(contentsBy);
    }

    @Override
    public List<ArticleBean> searchArticleByKey(BaseArticleBean articleList, int page, int pageSize) {
        List<ArticleBean> query = solrConfig.query(articleList.getSearchValue(), ArticleBean.class,page,pageSize);
        return query;
    }

    @Override
    public List<ArticleBean> searchArticle(BaseArticleBean articleList) {
        List<ArticleBean> query = solrConfig.query(articleList.getSearchValue(), ArticleBean.class,-1,-1);
        return query;
    }

    @Override
    public List<ArticleBean> getNewArticle() {
        return contentDao.getNewArticle();
    }

    @Override
    public List<CommentBean> getNewComment() {
        return contentDao.getNewComment();
    }

    @Override
    public Integer getArticleNum(int status) {
        String num = redisClient.get("status" + status);
        if (!StringUtils.isEmpty(num)) {
            return Integer.parseInt(num);
        }
        Integer contentsNum = contentDao.getContentsNum(status);
        redisClient.setex("status" + status, String.valueOf(contentsNum), (int) time);
        return contentsNum;
    }

    @Override
    public Integer getCategoryCount() {
        String categoryCount = redisClient.get("categoryCount");
        if (!StringUtils.isEmpty(categoryCount)) {
            return Integer.parseInt(categoryCount);
        }
        int categoryNum = contentDao.getCategoryNum();
        redisClient.setex("categoryCount", String.valueOf(categoryNum), (int) time);
        return categoryNum;
    }

    @Override
    public Integer getTagCount() {
        String tagCount = redisClient.get("tagCount");
        if (!StringUtils.isEmpty(tagCount)) {
            return Integer.parseInt(tagCount);
        }
        int tagNum = contentDao.getTagNum();
        redisClient.setex("tagCount", String.valueOf(tagNum), (int) time);
        return tagNum;
    }

    @Override
    public Integer getCommentsCount() {
        String commentsCount = redisClient.get("commentsCount");
        if (!StringUtils.isEmpty(commentsCount)) {
            return Integer.parseInt(commentsCount);
        }
        int cc = contentDao.getCommentsCount();
        redisClient.setex("commentsCount", String.valueOf(cc), (int) time);
        return cc;
    }

    @Override
    public void addCategory(CategoryBean category) {
        redisClient.del("categoryList");
        category.setCreateTime(new java.util.Date());
        category.setAid(UUID.UU64());
        contentDao.addCategory(category);
    }

    @Override
    public void addTag(Tag tag) {
        redisClient.del("tagList");
        tag.setCreateTime(new java.util.Date());
        tag.setAid(UUID.UU64());
        contentDao.addTag(tag);
    }

    @Override
    public PageInfo<Tag> getTagList() {
        String tagList = redisClient.get("tagList");
        if (!StringUtils.isEmpty(tagList)) {
            List<Tag> tl = JSON.parseArray(tagList, Tag.class);
            return new PageInfo<>(tl);
        }
        List<Tag> tl = contentDao.getTagList();
        redisClient.setex("tagList", JSON.toJSONString(tl), (int) time);
        return new PageInfo<>(tl);
    }

    @Override
    public PageInfo<CategoryBean> getCategoryList() {
        String categoryList = redisClient.get("categoryList");
        if (!StringUtils.isEmpty(categoryList)) {
            List<CategoryBean> cl = JSON.parseArray(categoryList, CategoryBean.class);
            return new PageInfo<>(cl);
        }
        List<CategoryBean> cl = contentDao.getCategoryList();
        redisClient.setex("categoryList", JSON.toJSONString(cl), (int) time);
        return new PageInfo<>(cl);
    }

    @Override
    public void deleteTag(String tagId) {
        redisClient.del("tagList");
        contentDao.deleteTag(tagId);
        contentDao.deleteArticleAndTag(tagId);
    }

    @Override
    public void deleteCategory(String categoryId) {
        redisClient.del("categoryList");
        contentDao.deleteCategory(categoryId);
        contentDao.deleteArticleAndCateory(categoryId);
    }

    @Override
    public void modifyCategory(String categoryId, String categoryName) {
        redisClient.del("categoryList");
        contentDao.modifyCategory(categoryId, categoryName);
    }

    @Override
    public void modifyTag(String tagId, String tagName) {
        redisClient.del("tagList");
        contentDao.modifyTag(tagId, tagName);
    }

    @Override
    public void publArticle(ArticleBean articleBean,List<Tag> tags) {
        redisClient.del("status0");
        redisClient.del("status1");
        redisClient.del("status2");
        articleBean.setCreateTime(new Date());
        articleBean.setPublishTime(new Date());
        String id = UUID.UU64();
        articleBean.setAid(id);
        tags.forEach(tag -> {
            String tid = tag.getAid();
            contentDao.bindArticleAndTag(id, tid, new Date());
          //  contentDao.setTagForArticleCount(tid);
        });
        contentDao.publArticle(articleBean);
    }

    @Override
    public void saveArticle(ArticleBean articleBean,List<Tag> tags) {
        redisClient.del("status0");
        redisClient.del("status1");
        redisClient.del("status2");
        articleBean.setCreateTime(new Date());
        articleBean.setStatus(2);
        String id = UUID.UU64();
        articleBean.setAid(id);
        tags.forEach(tag -> {
            String tid = tag.getAid();
            contentDao.bindArticleAndTag(id, tid, new Date());
          //  contentDao.setTagForArticleCount(tid);
        });
        contentDao.saveArticle(articleBean);
    }

    @Override
    public void modifyArticle(ArticleBean articleBean,List<Tag> tags) {
        redisClient.del("status0");
        redisClient.del("status1");
        redisClient.del("status2");
        articleBean.setUpdateTime(new Date());
        tags.forEach(tag -> {
            String tid = tag.getAid();
            contentDao.bindArticleAndTag(articleBean.getAid(), tid, new Date());
          //  contentDao.setTagForArticleCount(tid);
        });
        contentDao.modifyArticle(articleBean);
    }

    @Override
    public void deleteArticle(String id) {
        redisClient.del("status0");
        redisClient.del("status1");
        redisClient.del("status2");
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
        contentDao.setTagForArticleCount(tid);
    }

    @Override
    public CategoryBean getCategory(String categoryId) {
        String categoryList = redisClient.get("categoryList");
        if (!StringUtils.isEmpty(categoryList)) {
            List<CategoryBean> categoryBeans = JSON.parseArray(categoryList, CategoryBean.class);
            for (CategoryBean categoryBean : categoryBeans) {
                if (categoryBean != null && categoryBean.getAid().equals(categoryId)) {
                    return categoryBean;
                }
            }
        }
        return contentDao.getCategory(categoryId);
    }

    @Override
    public void bindArticleAndCategory(String cid) {
        contentDao.setCategoryForArticleCount(cid);
    }

    @Override
    public Tag getTag(String tagId) {
        String tagList = redisClient.get("tagList");
        if (!StringUtils.isEmpty(tagList)) {
            List<Tag> tags = JSON.parseArray(tagList, Tag.class);
            for (Tag tag : tags) {
                if (tag != null && tag.getAid().equals(tagId)) {
                    return tag;
                }
            }
        }
        return contentDao.getTag(tagId);
    }

    @Override
    public List<Tag> getTagByArticleId(String articleId) {
        return contentDao.getTagByArticleId(articleId);
    }

    @Override
    public PageInfo<FriendsBean> getFriendsList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);

        List<FriendsBean> friendsList = contentDao.getFriendsList();
        return new PageInfo<>(friendsList);
    }

    @Override
    public List<FriendTypeList> getFriendTypeList() {
        String friendTypeList = redisClient.get("friendTypeList");
        if (!StringUtils.isEmpty(friendTypeList)) {
            return JSON.parseArray(friendTypeList, FriendTypeList.class);
        }
        List<FriendTypeList> ftl = contentDao.getFriendTypeList();
        redisClient.setex("friendTypeList", JSON.toJSONString(ftl), (int) time);
        return ftl;

    }

    @Override
    public void addFriends(String name, String url, int typeId) {
        redisClient.del("friendsList");
        FriendsBean friendsBean = new FriendsBean();
        friendsBean.setFriendId(UUID.UU64());
        friendsBean.setName(name);
        friendsBean.setCreateTime(new Date());
        friendsBean.setTypeId(typeId);
        friendsBean.setUrl(url);
        contentDao.addFriends(friendsBean);
    }

    @Override
    public void deleteFriend(String fid) {
        redisClient.del("friendsList");
        contentDao.deleteFriend(fid);
    }
}
