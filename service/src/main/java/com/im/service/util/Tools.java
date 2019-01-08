package com.im.service.util;


/**
 * @author viruser
 * @create 2018/12/29
 * @since 1.0.0
 */
public class Tools {

    private final static String GENERAL_DATE = "statistical";
    private final static String ADD_COMMENT = "add_comment";
    private final static String CACHEE_KEY="MY-IM-CACHE-KEY";
    public static String getCacheArticleKey(int cid){
        String key = CACHEE_KEY+"ARTICLE"+cid;
        return key;
    }
    public static String getCacheCommentKey(int cid){
        String key = CACHEE_KEY+"COMMENT"+cid;
        return key;
    }

    public static String getGENERAL_DATE() {
        return GENERAL_DATE;
    }

    public static String getADD_COMMENT() {
        return ADD_COMMENT;
    }
}
