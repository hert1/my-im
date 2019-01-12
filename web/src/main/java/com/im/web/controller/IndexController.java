package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.article.IArticle;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.apiservice.article.ICategoryService;
import com.im.api.apiservice.user.IAdminService;
import com.im.api.apiservice.user.IUserService;
import com.im.api.dto.article.*;
import com.im.api.dto.user.AboutMeBean;
import com.im.redis.client.RedisClient;
import com.im.web.bean.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Reference
    IArticleService articleService;
    @Reference
    ICategoryService categoryService;

    @Reference
    IArticle article;
    @Autowired
    RedisClient redisClient;
    @Reference
    IUserService userService;



    /**
     * 获取友链列表
     * @return
     */
    @GetMapping(value = {"/friends/list"})
    @ResponseBody
    public BaseResponse getBlogFriendsList() {
        List<FriendsBean> friendsList = articleService.getFriendsList(1,44);
        return BaseResponse.ok(friendsList);
    }
    /**
     * 获取标签列表
     * @return
     */
    @GetMapping(value = {"/tag/list"})
    @ResponseBody
    public BaseResponse getBlogTagList() {
        List<Tag> tagList = articleService.getTagList();
        return BaseResponse.ok(tagList);
    }
    /**
     * 获取分类列表
     * @return
     */
    @GetMapping(value = {"/category/list"})
    @ResponseBody
    public BaseResponse getBlogCategoryList() {
        List<CategoryBean> categoryBeans  = articleService.getCategoryList();
        return BaseResponse.ok(categoryBeans);
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
        blogInfo.setBlogName(config.getBlog_name());
        blogInfo.setSign(config.getSign());
        blogInfo.setArticleCount(articleCount);
        blogInfo.setCategoryCount(categoryCount);
        blogInfo.setTagCount(tagCount);
        return new BaseResponse(true, "请求成功", 200, blogInfo);
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
        List<ArticleBean> articleByNumAndSize = articleService.getArticleByNumAndSize(articleList);//0为状态正常发布
        articleByNumAndSize.forEach(articleBean -> {
            ArticleInfo articleInfo = new ArticleInfo();
            String categoryId = articleBean.getCategoryId();
            List<CategoryBean> categoryByArticleCategoryId = categoryService.getCategoryByArticleCategoryId(categoryId);
            List<Tag> tagByArticleId = articleService.getTagByArticleId(articleBean.getId());
            articleInfo.setArticle(articleBean);
            articleInfo.setTags(tagByArticleId);
            if (categoryByArticleCategoryId != null && categoryByArticleCategoryId.size() > 0) {
                articleInfo.setCategory(categoryByArticleCategoryId.get(0));
            }
            articleInfoList.add(articleInfo);
        });
        indexResp.setList(articleInfoList);
        Integer articleNum = articleService.getArticleNum(0);
        indexResp.setCount(articleNum);
        return BaseResponse.ok(indexResp);

    }



}
