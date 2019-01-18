package com.im.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.im.api.apiservice.article.IArticle;
import com.im.api.dto.article.*;
import com.im.redis.client.RedisClient;
import com.im.service.bean.ConfigBean;
import com.im.service.dao.ArticleDao;
import com.im.service.dao.UserDao;
import com.im.service.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

/**
 * @author viruser
 * @create 2018/12/25
 * @since 1.0.0
 */
@Component
@Slf4j
@Service(version = "1.0",timeout = 3000)
public class ArticleImpl implements IArticle {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    ConfigBean configBean;
    @Autowired
    RedisClient redisClient;
    @Autowired
    UserDao userDao;

    @Override
    public ArticleBean getArticleByNum(String cid) {
        return articleDao.getContentsByNum(cid);
    }

    @Override
    public List<CommentBean> getCommentsByAid(String fid, int rid) {
        List<CommentBean> commentsByAid = articleDao.getCommentsByAid(fid, rid);
        if(commentsByAid!=null&&commentsByAid.size()>0){
            for (CommentBean commentBean : commentsByAid) {
                Integer id = commentBean.getId();
                List<CommentBean> commentsByAid1 = getCommentsByAid(fid, id);
                if(commentsByAid1==null&&commentsByAid1.size()==0) {
                    return commentsByAid;
                }
                commentBean.setChildren(commentsByAid1);
            }

        }

        return commentsByAid;
    }


    @Override
    public int insertComments(CommentBean commentBean) {
        articleDao.comment(commentBean);
        return 1;
    }


}
