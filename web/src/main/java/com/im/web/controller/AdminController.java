package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.article.IArticle;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.apiservice.article.ICategoryService;
import com.im.api.apiservice.user.IAdminService;
import com.im.api.dto.article.*;
import com.im.api.dto.user.AboutMeBean;
import com.im.api.dto.user.BlogConfigBean;
import com.im.api.util.UUID;
import com.im.web.bean.*;
import com.im.web.config.FastDFSClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "a")
public class AdminController {

    @Autowired
    FastDFSClient fastDFSClient ;


    @Value("${fastdfs.http_secret_key}")
    public  String httpSecretKey;

    @Value("${file_server_addr}")
    public  String fileServerAddr;

    @Reference
    IAdminService adminService;
    @Reference
    IArticleService articleService;
    @Reference
    IArticle article;
    @Reference
    ICategoryService categoryService;

    /**
     * 添加分类
     *
     * @param categoryName
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/category/add")
    @ResponseBody
    public BaseResponse addCategory(@RequestParam String categoryName) throws Exception {
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setName(categoryName);
        categoryBean.setId(UUID.UU64());
        articleService.addCategory(categoryBean);
        return BaseResponse.ok();

    }
    /**
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload/image")
    @ResponseBody
    public BaseResponse getQiniuToken(@RequestParam(value = "file") MultipartFile multipartFile) throws Exception {
        String path = fastDFSClient.uploadFileWithMultipart(multipartFile);
        return BaseResponse.ok(path);
    }

    /**
     * 添加标签
     *
     * @param tagName
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/tag/add")
    @ResponseBody
    public BaseResponse addTag(@RequestParam String tagName) throws Exception {
        Tag tag = new Tag();
        tag.setId(UUID.UU64());
        tag.setName(tagName);
        articleService.addTag(tag);
        return BaseResponse.ok();

    }


    /**
     * 获取分类列表
     *
     * @param all
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/category/list")
    @ResponseBody
    public BaseResponse getCategoryList(@RequestParam Boolean all) throws Exception {
        List<CategoryBean> categoryBeans = null;
        if (all) {
            categoryBeans  = articleService.getCategoryList();

        }
        return BaseResponse.ok(categoryBeans);
    }

    /**
     * 首页
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/statistics/home")
    @ResponseBody
    public BaseResponse getHome() throws Exception {
        Integer publishCount = articleService.getArticleNum(0);//状态正常的文章总数
        Integer draftsCount = articleService.getArticleNum(2);//草稿箱
        Integer deletedCount = articleService.getArticleNum(1);//删除
        Integer categoryCount = articleService.getCategoryCount();
        Integer tagCount = articleService.getTagCount();
        Integer commentsCount = articleService.getCommentsCount();
        StatisticsResp resp = new StatisticsResp();
        resp.setCategoryCount(categoryCount);
        resp.setCommentsCount(commentsCount);
        resp.setDeletedCount(deletedCount);
        resp.setDraftsCount(draftsCount);
        resp.setPublishCount(publishCount);
        resp.setTagCount(tagCount);
        return BaseResponse.ok(resp);
    }

    /**
     * 获取文章列表
     */
    @PostMapping(value = "/article/list")
    @ResponseBody
    public BaseResponse getList(@RequestBody GetArticleListReq req) throws Exception {
        BaseArticleBean articleList = new BaseArticleBean();
        BeanUtils.copyProperties(req,articleList);
        List<ArticleBean> articleByNumAndSize = articleService.getArticleByNumAndSize(articleList);//0为状态正常发布
        return BaseResponse.ok(articleByNumAndSize);
    }

    /**
     * 获取日志
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sys/log")
    @ResponseBody
    public BaseResponse getLog(@RequestParam int page
            , @RequestParam int pageSize) throws Exception {
        return BaseResponse.ok();
    }

    /**
     * 获取标签列表
     *
     * @param all
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/tag/list")
    @ResponseBody
    public BaseResponse getTagList(@RequestParam Boolean all) throws Exception {
        List<Tag> tagList = null;
        if (all) {
            tagList  = articleService.getTagList();

        }
        return BaseResponse.ok(tagList);

    }

    /**
     * 获取配置
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/webConfig")
    @ResponseBody
    public BaseResponse getWebConfig() throws Exception {

        BlogConfigBean webConfig = adminService.getWebConfig();
        BlogConfigResp blogConfigResp = new BlogConfigResp();
        BeanUtils.copyProperties(webConfig, blogConfigResp);
        blogConfigResp.setAlipayQrcode(webConfig.getAlipay_qrcode());
        blogConfigResp.setBlogName(webConfig.getBlog_name());
        blogConfigResp.setWxpayQrcode(webConfig.getWxpay_qrcode());
        blogConfigResp.setViewPassword(webConfig.getView_password());
        blogConfigResp.setHadOldPassword(false);
        return BaseResponse.ok(blogConfigResp);
    }

    /**
     * 修改关于我
     *
     * @param aboutMeContent
     * @param htmlContent
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/webConfig/modifyAbout")
    @ResponseBody
    public BaseResponse setAboutMe(@RequestParam String aboutMeContent, @RequestParam String htmlContent) throws Exception {
        AboutMeBean aboutMeBean = new AboutMeBean();
        aboutMeBean.setHtml(htmlContent);
        aboutMeBean.setMd(aboutMeContent);
        adminService.setAboutMe(aboutMeBean);
        return BaseResponse.ok();

    }

    /**
     * 修改配置
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/webConfig/modify")
    @ResponseBody
    public BaseResponse setWebConfig(@RequestBody UpdataConfigReq req) throws Exception {
        BlogConfigBean blogConfigBean = new BlogConfigBean();
        BeanUtils.copyProperties(req, blogConfigBean);
        blogConfigBean.setAlipay_qrcode(req.getAlipayQrcode());
        blogConfigBean.setBlog_name(req.getBlogName());
        blogConfigBean.setWxpay_qrcode(req.getWxpayQrcode());
        blogConfigBean.setView_password(req.getViewPassword());
        adminService.setWebConfig(blogConfigBean);
        return BaseResponse.ok();
    }

    /**
     * 删除标签
     *
     * @param tagId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/tag/delete")
    @ResponseBody
    public BaseResponse deleteTag(@RequestParam String tagId) throws Exception {
        articleService.deleteTag(tagId);
        return BaseResponse.ok();

    }

    /**
     * 删除分类
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/category/delete")
    @ResponseBody
    public BaseResponse deleteCategory(@RequestParam String categoryId) throws Exception {
        articleService.deleteCategory(categoryId);
        return BaseResponse.ok();

    }
    /**
     * 修改分类
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/category/modify")
    @ResponseBody
    public BaseResponse modifyCategory(@RequestParam String categoryId,@RequestParam String categoryName) throws Exception {
        articleService.modifyCategory(categoryId,categoryName);
        return BaseResponse.ok();

    }

    /**
     * 修改标签
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/tag/modify")
    @ResponseBody
    public BaseResponse modifyTag(@RequestParam String tagId,@RequestParam String tagName) throws Exception {
        articleService.modifyTag(tagId,tagName);
        return BaseResponse.ok();

    }
    /**
     * 发布文章
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/article/publish")
    @ResponseBody
    public BaseResponse publArticle(@RequestBody AddBlogReq req) throws Exception {
        ArticleBean articleBean = new ArticleBean();
        BeanUtils.copyProperties(req,articleBean);
        articleBean.setCategoryId(req.getCategory().getId());
        articleBean.setCreateTime(new Date());
        articleBean.setPublishTime(new Date());
        String id = UUID.UU64();
        articleBean.setId(id);
        List<Tag> tags = req.getTags();
        tags.forEach(tag -> {
            String tid = tag.getId();
            articleService.bindArticleAndTag(id,tid);
        });
        articleService.publArticle(articleBean);
        return BaseResponse.ok(id);

    }
    /**
     * save章
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/article/save")
    @ResponseBody
    public BaseResponse saveArticle(@RequestBody AddBlogReq req) throws Exception {
        ArticleBean articleBean = new ArticleBean();
        BeanUtils.copyProperties(req,articleBean);
        if(req.getCategory()!=null) {
            articleBean.setCategoryId(req.getCategory().getId());
        }
        articleBean.setCreateTime(new Date());
        articleBean.setStatus(2);
        String id = UUID.UU64();
        articleBean.setId(id);
        List<Tag> tags = req.getTags();
        tags.forEach(tag -> {
            String tid = tag.getId();
            articleService.bindArticleAndTag(id,tid);
        });
        articleService.saveArticle(articleBean);
        return BaseResponse.ok(id);

    }
    /**
     * 编辑文章
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/article/modify")
    @ResponseBody
    public BaseResponse modifyArticle(@RequestBody AddBlogReq req) throws Exception {
        ArticleBean articleBean = new ArticleBean();
        BeanUtils.copyProperties(req,articleBean);
        articleBean.setCategoryId(req.getCategory().getId());
        BeanUtils.copyProperties(req,articleBean);
        articleBean.setCategoryId(req.getCategory().getId());
        articleBean.setUpdateTime(new Date());
        List<Tag> tags = req.getTags();
        tags.forEach(tag -> {
            String tid = tag.getId();
            articleService.bindArticleAndTag(articleBean.getId(),tid);
        });
        articleService.modifyArticle(articleBean);
        return BaseResponse.ok(articleBean.getId());

    }
    /**
     * 删除文章
     * @throws Exception
     */
    @PostMapping(value = "/article/delete")
    @ResponseBody
    public BaseResponse deleteArticle(@RequestParam String articleId) throws Exception {
        articleService.deleteArticle(articleId);
        return BaseResponse.ok();

    }
    /**
     * 获取分类
     * @throws Exception
     */
    @GetMapping(value = "/category")
    @ResponseBody
    public BaseResponse getCategory(@RequestParam String categoryId) throws Exception {
        CategoryBean category = articleService.getCategory(categoryId);
        return BaseResponse.ok(category);
    }
    /**
     * 获取标签
     * @throws Exception
     */
    @GetMapping(value = "/tag")
    @ResponseBody
    public BaseResponse getTag(@RequestParam String tagId) throws Exception {
        Tag tag = articleService.getTag(tagId);
        return BaseResponse.ok(tag);
    }
    /**
     * 获取友链列表
     * @throws Exception
     */
    @GetMapping(value = "/friends/list")
    @ResponseBody
    public BaseResponse getFriendsList(@RequestParam int page,@RequestParam int pageSize) throws Exception {
        List<FriendsBean> friendsList = articleService.getFriendsList(page, pageSize);
        return BaseResponse.ok(friendsList);
    }
    /**
     * 添加友链
     * @throws Exception
     */
    @PostMapping(value = "/friends/add")
    @ResponseBody
    public BaseResponse addFriend(@RequestParam String name,@RequestParam String url,@RequestParam int typeId) throws Exception {
        articleService.addFriends(name,url,typeId);
        return BaseResponse.ok();
    }
    /**
     * 编辑友链
     * @throws Exception
     */
    @PostMapping(value = "/friends/modify")
    @ResponseBody
    public BaseResponse modifyFriend(@RequestParam String tagId) throws Exception {
        return BaseResponse.ok();
    }
    /**
     * 删除友链
     * @throws Exception
     */
    @PostMapping(value = "/friends/delete")
    @ResponseBody
    public BaseResponse deleteFriend(@RequestParam String friendId) throws Exception {
        articleService.deleteFriend(friendId);
        return BaseResponse.ok();
    }
    /**
     * 获取友链类型列表
     * @throws Exception
     */
    @GetMapping(value = "/friends/typeList")
    @ResponseBody
    public BaseResponse getFriendTypeList() throws Exception {
        List<FriendTypeList> friendTypeList = articleService.getFriendTypeList();
        return BaseResponse.ok(friendTypeList);
    }

    /**
     * 回复
     * @param articleId
     * @param name
     * @param replyId
     * @param content
     * @param sourceContent
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
            , @RequestParam String ticket
            , @RequestParam String randstr
            , @RequestParam(defaultValue = "0") int parentId) {
        CommentBean commentBean = new CommentBean();
        commentBean.setName(name);
        commentBean.setArticle_id(articleId);
        commentBean.setParent_id(parentId);
        commentBean.setReply_id(replyId);
        commentBean.setContent(content);
        commentBean.setSource_content(sourceContent);
        commentBean.setCreate_time(new java.sql.Date(System.currentTimeMillis()));
        int i = article.insertComments(commentBean);
        return new BaseResponse(true, "请求成功", 200, i);
    }
}
