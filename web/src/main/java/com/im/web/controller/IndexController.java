package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.apiservice.article.ICategoryService;
import com.im.api.apiservice.user.IUserService;
import com.im.api.dto.article.*;
import com.im.redis.client.RedisClient;
import com.im.web.bean.Article;
import com.im.web.bean.BlogInfo;
import com.im.web.bean.IndexResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "w/article")
public class IndexController {

    @Reference
    IArticleService articleService;
    @Reference
    ICategoryService categoryService;
    @Reference
    IUserService userService;
    @Autowired
    RedisClient redisClient;

    @GetMapping(value = {"/getAbout"})
    @ResponseBody
    public BaseResponse getAbout() {

        return null;
    }

    @GetMapping(value = {"/archives"})
    @ResponseBody
    public BaseResponse getArticleArchives(HttpServletRequest request
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
            , @RequestParam(value = "page") int page) throws Exception {

        return null;

    }

    @GetMapping(value = {"/blogInfo"})
    @ResponseBody
    public BaseResponse getInfo(HttpServletRequest request) {
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
     * @param request
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/list"})
    @ResponseBody
    public BaseResponse index(HttpServletRequest request
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
            , @RequestParam(value = "page") int page) throws Exception {
        IndexResp indexResp = new IndexResp();
        List<Article> list = new ArrayList<>();
        Article article = new Article();
        List<ArticleBean> articleByNumAndSize = articleService.getArticleByNumAndSize(page, pageSize,0);//0为状态正常发布
        articleByNumAndSize.forEach(articleBean -> {
            String id = articleBean.getId();
            String categoryId = articleBean.getCategoryId();
            List<CategoryBean> categoryByArticleCategoryId = categoryService.getCategoryByArticleCategoryId(categoryId);
            article.setCategory(categoryByArticleCategoryId);
            Tag tag = new Tag();
            tag.setLength(-1);
            article.setTags(tag);
            article.setArticle(articleBean);
            list.add(article);
        });
        indexResp.setList(list);
        indexResp.setCount(list.size());
        return new BaseResponse(true, "请求成功", 200, indexResp);

    }

}
