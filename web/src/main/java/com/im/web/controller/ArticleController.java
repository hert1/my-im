package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.article.IArticle;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.apiservice.article.ICategoryService;
import com.im.api.apiservice.user.IAdminService;
import com.im.api.dto.article.*;
import com.im.api.dto.user.AboutMeBean;
import com.im.redis.client.RedisClient;
import com.im.web.annotation.SessionCheck;
import com.im.web.bean.ArticleResp;
import com.im.web.bean.IndexResp;
import com.im.web.util.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author viruser
 * @create 2018/12/25
 * @since 1.0.0
 */
@Controller
public class ArticleController {

    @Reference(version = "1.0",timeout = 5000)
    IArticleService articleService;
    @Reference(version = "1.0",timeout = 5000)
    IArticle article;
    @Reference(version = "1.0",timeout = 5000)
    IAdminService adminService;
    @Reference(version = "1.0",timeout = 5000)
    ICategoryService categoryService;
    @Autowired
    RedisClient redisClient;
    @Autowired
    UserSession userSession;

    /**
     *添加回复
     * @param articleId
     * @param name
     * @param replyId
     * @param content
     * @param sourceContent
     * @param email
     * @param ticket
     * @param randstr
     * @param parentId
     * @return
     */
    @PostMapping(value = "/w/comments/add")
    public @ResponseBody
    BaseResponse comment(@RequestParam String articleId
            , @RequestParam String name
            , @RequestParam int replyId
            , @RequestParam String content
            , @RequestParam String sourceContent
            , @RequestParam String email
            , @RequestParam String ticket
            , @RequestParam String randstr
            , @RequestParam(defaultValue = "0") int parentId) {
        CommentBean commentBean = new CommentBean();
        commentBean.setName(name);
        commentBean.setArticleId(articleId);
        commentBean.setParentId(parentId);
        commentBean.setReplyId(replyId);
        commentBean.setEmail(email);
        commentBean.setContent(content);
        commentBean.setSourceContent(sourceContent);
        commentBean.setCreateTime(new Date(System.currentTimeMillis()));
        int i = article.insertComments(commentBean);
        return new BaseResponse(true, "请求成功", 200, i);
    }

    /**
     * 获取评论
     *
     * @param id
     * @return
     */
    @GetMapping(value = {"/w/comments/list","/a/comments/list"})
    public @ResponseBody
    BaseResponse getComment(@RequestParam(value = "articleId") String id) {
        IndexResp indexResp = new IndexResp();
        List<CommentBean> commentsByAid = article.getCommentsByAid(id, 0);
        indexResp.setList(commentsByAid);
        indexResp.setCount(Long.valueOf(commentsByAid.size()));
        return new BaseResponse(true, "请求成功", 200, indexResp);
    }

    /**
     * 获取文章信息
     * @param id
     * @return
     */
    @GetMapping(value = {"/w/article","/a/article/info"})
    @ResponseBody
    public BaseResponse getBlogArticle(@RequestParam String id) {
        ArticleBean articleByNum = article.getArticleByNum(id);
        String categoryId = articleByNum.getCategoryId();
        List<CategoryBean> categoryByArticleCategoryId = categoryService.getCategoryByArticleCategoryId(categoryId);
        List<Tag> tagByArticleId = articleService.getTagByArticleId(id);
        ArticleResp articleResp = new ArticleResp();
        articleResp.setTags(tagByArticleId);
        articleResp.setArticle(articleByNum);
        if (categoryByArticleCategoryId!=null&&categoryByArticleCategoryId.size()>0) {
            articleResp.setCategory(categoryByArticleCategoryId.get(0));
        }
        return BaseResponse.ok(articleResp);
    }

    /**
     * 关于我
     * @return
     */
    @GetMapping(value = {"/w/article/getAbout","/a/webConfig/getAbout"})
    @ResponseBody
    public BaseResponse getAbout() {
        AboutMeBean aboutMe = adminService.getAboutMe();
        return BaseResponse.ok(aboutMe);
    }
}
