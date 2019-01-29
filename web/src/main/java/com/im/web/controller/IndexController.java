package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.apiservice.article.ICategoryService;
import com.im.api.apiservice.user.IUserService;
import com.im.api.dto.article.*;
import com.im.redis.client.RedisClient;
import com.im.web.bean.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "w")
public class IndexController {

    @Reference(version = "1.0",timeout = 5000)
    IArticleService articleService;
    @Reference(version = "1.0",timeout = 5000)
    ICategoryService categoryService;
    @Autowired
    RedisClient redisClient;
    @Reference(version = "1.0",timeout = 5000)
    IUserService userService;



    /**
     * 获取友链列表
     * @return
     */
    @GetMapping(value = {"/friends/list"})
    @ResponseBody
    public BaseResponse getBlogFriendsList() {
        PageInfo<FriendsBean> friendsList = articleService.getFriendsList(1,44);
        return BaseResponse.ok(friendsList.getList());
    }
    /**
     * 获取标签列表
     * @return
     */
    @GetMapping(value = {"/tag/list"})
    @ResponseBody
    public BaseResponse getBlogTagList() {
        PageInfo<Tag> tagList = articleService.getTagList();
        return BaseResponse.ok(tagList.getList());
    }
    /**
     * 获取分类列表
     * @return
     */
    @GetMapping(value = {"/category/list"})
    @ResponseBody
    public BaseResponse getBlogCategoryList() {
        PageInfo<CategoryBean> categoryBeans  = articleService.getCategoryList();
        return BaseResponse.ok(categoryBeans.getList());
    }

    /**
     *
     * blog info
     * @return
     */
    @GetMapping(value = {"/article/info"})
    @ResponseBody
    public BaseResponse getInfo() {
        Integer articleCount = articleService.getArticleNum(0);
        Integer categoryCount = articleService.getCategoryCount();
        Integer tagCount = articleService.getTagCount();
        BlogInfoBean config = userService.getBlogInfo();
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setBlogName(config.getBlogName());
        blogInfo.setSign(config.getSign());
        blogInfo.setArticleCount(articleCount);
        blogInfo.setCategoryCount(categoryCount);
        blogInfo.setTagCount(tagCount);
        return new BaseResponse(true, "请求成功", 200, blogInfo);
    }

    /**
     *
     * blog article info
     * @return
     */
    @GetMapping(value = {"/article_blog"})
    @ResponseBody
    public BaseResponse getBlogArticleInfo() {
        ArticleBaseBlogResp resp = new ArticleBaseBlogResp();
        List<ArticleBean> newArticle = articleService.getNewArticle();
        List<CommentBean> newComment = articleService.getNewComment();
        resp.setNewArticle(newArticle);
        resp.setNewComment(newComment);
        return BaseResponse.ok(resp);
    }
    /**
     * 首页
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/article/list","/article/archives"})
    @ResponseBody
    public BaseResponse index(@RequestBody GetArticleListReq req) throws Exception {
        IndexResp indexResp = new IndexResp();
        List<ArticleInfo> articleInfoList = new LinkedList<>();
        BaseArticleBean articleList = new BaseArticleBean();
        BeanUtils.copyProperties(req,articleList);
        PageInfo<ArticleBean> articleByNumAndSize = articleService.getArticleList(articleList);//0为状态正常发布
        articleByNumAndSize.getList().forEach(articleBean -> {
            ArticleInfo articleInfo = new ArticleInfo();
            String categoryId = articleBean.getCategoryId();
            List<CategoryBean> categoryByArticleCategoryId = categoryService.getCategoryByArticleCategoryId(categoryId);
            List<Tag> tagByArticleId = articleService.getTagByArticleId(articleBean.getAid());
            articleInfo.setArticle(articleBean);
            articleInfo.setTags(tagByArticleId);
            if (categoryByArticleCategoryId != null && categoryByArticleCategoryId.size() > 0) {
                articleInfo.setCategory(categoryByArticleCategoryId.get(0));
            }
            articleInfoList.add(articleInfo);
        });
        indexResp.setList(articleInfoList);
        indexResp.setCount(articleByNumAndSize.getTotal());
        return BaseResponse.ok(indexResp);

    }

    /**
     * 按文章标题和简介搜索
     * @return
     */
    @PostMapping(value = {"/article/search"})
    @ResponseBody
    public BaseResponse searchArticle(@RequestBody GetArticleListReq req) {
        IndexResp indexResp = new IndexResp();
        List<ArticleInfo> articleInfoList = new LinkedList<>();
        BaseArticleBean articleList = new BaseArticleBean();
        BeanUtils.copyProperties(req,articleList);
        List<ArticleBean> articleBeans = articleService.searchArticleByKey(articleList, req.getPage(), req.getPageSize());
        articleBeans.forEach(articleBean -> {
            ArticleInfo articleInfo = new ArticleInfo();
            String categoryId = articleBean.getCategoryId();
            List<CategoryBean> categoryByArticleCategoryId = categoryService.getCategoryByArticleCategoryId(categoryId);
            List<Tag> tagByArticleId = articleService.getTagByArticleId(articleBean.getAid());
            articleInfo.setArticle(articleBean);
            articleInfo.setTags(tagByArticleId);
            if (categoryByArticleCategoryId != null && categoryByArticleCategoryId.size() > 0) {
                articleInfo.setCategory(categoryByArticleCategoryId.get(0));
            }
            articleInfoList.add(articleInfo);
        });
        indexResp.setList(articleInfoList);
        Integer articleNum = articleService.searchArticle(articleList).size();
        indexResp.setCount(Long.valueOf(articleNum));
        return BaseResponse.ok(indexResp);
    }
}
