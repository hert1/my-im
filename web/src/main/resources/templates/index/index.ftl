<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <@head title="${blogTitle}">
        <#if metaKeywords??>
        <meta name="keywords" content="${metaKeywords}"/>
        </#if>
        <#if metaDescription??>
        <meta name="description" content="${metaDescription}"/>
        </#if>
        </@head>
    </head>
    <body>
        ${topBarReplacement}
        <#include "nav.ftl">
        <div class="wrapper">
            <div class="content">
                <#include "header.ftl">
                <div class="roundtop"></div>
                <div class="body">
                    <div class="left main">
                        <#include "article-list.ftl">
                    </div>
                    <div class="right">
                        <#include "side.ftl">
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="roundbottom"></div>
            </div>
        </div>
        <div class="footer">
            <div class="footer-icon"><#include "statistic.ftl"></div>
            <#include "footer.ftl">
        </div>
        <script src="/static/js/article.js"></script>
    </body>
</html>
