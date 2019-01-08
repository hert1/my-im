package com.im.service.config;

import com.im.redis.client.RedisClient;
import com.im.service.impl.ArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author viruser
 * @create 2018/12/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class ApplicationStartup  {

    @Autowired
    RedisClient redisClient;
    @Autowired
    ArticleServiceImpl iContentService;

  //  @PostConstruct
    public void init() {
      /*  log.debug("start initing");
        redisClient.set("blogTitle", themeBean.getBlogTitle());//标题
        redisClient.set("blogSubtitle", themeBean.getBlogSubtitle());//标签

        List<ArticleBean> firstPage = iContentService.getArticleByNumAndSize(1, 10);
        redisClient.set("firstPage", JSONObject.toJSONString(firstPage));//首页
        List<ArticleBean> commentByTop = iContentService.getCommentsByArticle();
        redisClient.set("commentByTop", JSONObject.toJSONString(commentByTop));//回复数前10
        List<ArticleBean> viewByTop = iContentService.getViewByArticle();
        redisClient.set("viewByTop", JSONObject.toJSONString(viewByTop));//浏览数前10
        List<Tag> tags = iContentService.getTags();//获取所有标签
        redisClient.set("tags", JSONObject.toJSONString(tags));//获取所有标签
        StatisticBean statistical = iContentService.getStatistical();//获取所有文章浏览，回复，文章总数
        redisClient.set(com.im.service.util.Tools.getGENERAL_DATE(), JSONObject.toJSONString(statistical));//获取所有标签
        log.debug("end initing");*/


    }
}
