/*
package com.im.service.config;

*/
/**
 * @author viruser
 * @create 2018/12/29
 * @since 1.0.0
 *//*


import com.alibaba.fastjson.JSONObject;
import com.im.api.dto.article.CommentBean;
import com.im.redis.client.RedisClient;
import com.im.service.dao.ArticleDao;
import com.im.service.impl.ArticleImpl;
import com.im.service.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


*/
/**
 * 定时将redis里面的数据持久化到数据库
 *//*

@Component
public class ScheduledController {
    @Autowired
    RedisClient redisClient;
    @Autowired
    ArticleImpl articleImpl;
    @Autowired
    ApplicationStartup applicationStartup;
    @Autowired
    ArticleDao articleDao;
    */
/*
    时间控制，单位毫秒
     *//*

    private final long timeCache = 1000 * 5;

 //   @Scheduled(fixedDelay = timeCache)
    public void pushDatabyRedisToDb() {
        StatisticBean statistical = JSONObject.parseObject(redisClient.get(Tools.getGENERAL_DATE()), StatisticBean.class);
        int i = articleDao.updateStatistic(statistical);
        System.out.println(i);

        while(true) {
            String lpop = redisClient.lpop(Tools.getADD_COMMENT());
            if(lpop==null||"".equals(lpop)) {
                break;
            }
            CommentBean commentBean = JSONObject.parseObject(lpop, CommentBean.class);
            articleDao.comment(commentBean);
        }


            applicationStartup.init ();


    }


}
*/
