package com.im.api.apiservice.article;


import com.im.api.dto.article.*;

import java.util.List;

/**
 * @author viruser
 * @create 2018/12/25
 * @since 1.0.0
 */
public interface IArticle {

    /**\
     *通过cid查文章
     * @return
     */
    public ArticleBean getArticleByNum(String cid);
    /**
     *通过aid查回复
     * @return
     */
    public List<CommentBean> getCommentsByAid(String fid,int rid);

    /**
     * 回复
     * @return
     */
    public int insertComments(CommentBean req);
}
