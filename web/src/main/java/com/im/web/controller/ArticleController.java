package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.article.IArticle;
import com.im.api.apiservice.article.ICategoryService;
import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.BaseResponse;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.CommentBean;
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
@RequestMapping(value = "w")
public class ArticleController {

    @Reference
    IArticle article;
    @Reference
    ICategoryService categoryService;
    @Autowired
    RedisClient redisClient;
    @Autowired
    UserSession userSession;

    /**
     *回复
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
    @PostMapping(value = "/comments/add")
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
        commentBean.setArticle_id(articleId);
        commentBean.setParent_id(parentId);
        commentBean.setReply_id(replyId);
        commentBean.setEmail(email);
        commentBean.setContent(content);
        commentBean.setSource_content(sourceContent);
        commentBean.setCreate_time(new Date(System.currentTimeMillis()));
        int i = article.insertComments(commentBean);
        return new BaseResponse(true, "请求成功", 200, i);
    }

    /**
     * 通过文章Id到查看文章
     *
     * @param cid
     * @return
     */
    @GetMapping(value = {"/article"})
    @ResponseBody
    public BaseResponse getArticle(@RequestParam(value = "id") int cid) {
        ArticleResp articleResp = new ArticleResp();
        ArticleBean contentsById = article.getArticleByNum(cid);
        List<CategoryBean> categoryByArticleCategoryId = categoryService.getCategoryByArticleCategoryId(contentsById.getCategoryId());
        articleResp.setArticle(contentsById);
        articleResp.setCategory(categoryByArticleCategoryId);
        articleResp.setTags(null);

        return new BaseResponse(true, "请求成功", 200, articleResp);
    }

    /**
     * 获取评论
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/comments/list")
    public @ResponseBody
    BaseResponse getComment(@RequestParam(value = "articleId") int id) {
        IndexResp indexResp = new IndexResp();
        List<CommentBean> commentsByAid = article.getCommentsByAid(id, 0);
        indexResp.setList(commentsByAid);
        indexResp.setCount(commentsByAid.size());
        return new BaseResponse(true, "请求成功", 200, indexResp);
    }
}
