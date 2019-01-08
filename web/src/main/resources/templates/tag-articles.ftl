<#include "index/macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${tag.tagTitle} - ${blogTitle}">
        <meta name="keywords" content="${metaKeywords},${tag.tagTitle}"/>
        <meta name="description" content="<#list articles as article>${article.articleTitle}<#if article_has_next>,</#if></#list>"/>
        </@head>
    </head>
    <body>
        ${topBarReplacement}
        <#include "index/nav.ftl">
        <div class="wrapper">
            <div class="content">
                <#include "index/header.ftl">
                <div class="roundtop"></div>
                <div class="body">
                    <div class="left main">
                        <div class="kind-title">
                            ${tag1Label}
                        </div>
                        <div class="kind-panel">
                            <a rel="alternate" href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}"><span id="tagArticlesTag">
                                ${tag.tagTitle}
                            </span>(${tag.tagPublishedRefCount})</a>
                        </div>
                        <#include "index/article-list.ftl">
                    </div>
                    <div class="right">
                        <#include "index/side.ftl">
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="roundbottom"></div>
            </div>
        </div>
        <div class="footer">
            <div class="footer-icon"><#include "index/statistic.ftl"></div>
            <#include "index/footer.ftl">
        </div>
    </body>
</html>
