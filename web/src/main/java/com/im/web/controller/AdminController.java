package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.article.IArticleService;
import com.im.api.apiservice.user.IAdminService;
import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.BaseResponse;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;
import com.im.api.dto.user.AboutMeBean;
import com.im.api.dto.user.BlogConfigBean;
import com.im.api.util.UUID;
import com.im.web.bean.AddBlogReq;
import com.im.web.bean.BlogConfigResp;
import com.im.web.bean.StatisticsResp;
import com.im.web.bean.UpdataConfigReq;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Reference
    IAdminService adminService;
    @Reference
    IArticleService articleService;

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

    @GetMapping(value = "/webConfig/getAbout")
    @ResponseBody
    public BaseResponse getAboutMe() throws Exception {

        AboutMeBean aboutMe = adminService.getAboutMe();
        return BaseResponse.ok(aboutMe);
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
    @GetMapping(value = "/article/list")
    @ResponseBody
    public BaseResponse getList(@RequestParam String by
            , @RequestParam int status
            , @RequestParam int page
            , @RequestParam int pageSize) throws Exception {

        List<ArticleBean> articleByNumAndSize = articleService.getArticleByNumAndSize(page, pageSize, status);//0为状态正常发布

        return BaseResponse.ok(articleByNumAndSize);
    }

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
        String id = UUID.UU64();
        articleBean.setId(id);
        List<Tag> tags = req.getTags();
        tags.forEach(tag -> {
            String tid = tag.getId();
            articleService.bindArticleAndTag(id,tid);
        });
        articleService.publArticle(articleBean);
        return BaseResponse.ok();

    }
}
