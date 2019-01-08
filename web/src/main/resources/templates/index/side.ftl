<div id="sideNavi" class="side-navi">
    <div class="rings"></div>
    <div class="null"></div>
    <#if "" != "公告">
    <div class="item">
        <div class="antefatto">
            <h4 style="padding: 18px 0 0 80px">公告</h4>
        </div>
        <div class="marginLeft12 marginTop12">
            小而美的 Java 博客系统 Solo
        </div>
    </div>
    <div class="line"></div>
    </#if>
    <#--<#if 0 != recentComments?size>
    <div class="item navi-comments">
        <div class="ads">
            <h4 id="recentComments">最新评论</h4>
        </div>
        <ul>
            <#list recentComments as comment>
            <li>
                <img class='left' title='${comment.commentName}'
                     alt='${comment.commentName}'
                     src='${comment.commentThumbnailURL}'/>
                <div class='left'>
                    <div>
                        <a href="${comment.commentURL}">
                            ${comment.commentName}
                        </a>
                    </div>
                    <div class="comm">
                        <a rel="nofollow" class='side-comment' href="">
                            ${comment.commentContent}
                        </a>
                    </div>
                </div>
                <div class='clear'></div>
            </li>
            </#list>
        </ul>
    </div>
    <div class="line"></div>
    </#if>-->
    <#if 0 != mostCommentArticles?size>
    <div class="item">
        <div class="esclamativo">
            <h4 style="padding: 18px 0 0 80px">评论最多的文章</h4>
        </div>
        <ul id="mostCommentArticles">
            <#list mostCommentArticles as article>
            <li>
                <a rel="nofollow" title="${article.articleTitle}" href="">
                    ${article.articleTitle}
                </a>(${article.articleCommentCount})
            </li>
            </#list>
        </ul>
    </div>
    <div class="line"></div>
    </#if>
    <#if 0 != mostViewCountArticles?size>
    <div class="item">
        <div class="cuore">
            <h4 style="padding: 18px 0 0 80px">访问最多的文章</h4>
        </div>
        <ul id="mostViewCountArticles">
            <#list mostViewCountArticles as article>
            <li>
                <a rel="nofollow" title="${article.articleTitle}" href="${servePath}${article.articlePermalink}">
                    ${article.articleTitle}
                </a>(${article.articleViewCount})
            </li>
            </#list>
        </ul>
    </div>
    <div class="line"></div>
    </#if>

  <#--  <#if 0 != mostUsedCategories?size>
        <div class="item">
            <div class="categorie">
                <h4>分类</h4>
            </div>
            <ul id="mostViewCountArticles">
                <#list mostUsedCategories as category>
                    <li>
                        <a href="${servePath}/category/${category.categoryURI}"
                           title="${category.categoryTitle} (${category.categoryTagCnt})">
                            ${category.categoryTitle}</a>(${category.categoryTitle})
                    </li>
                </#list>
            </ul>
        </div>
        <div class="line"></div>
    </#if>-->

    <#if 0 != mostUsedTags?size>
    <div class="item">
        <div class="tags">
            <h4 style="padding: 18px 0 0 80px">标签</h4>
        </div>
        <ul class="mostViewCountArticles">
            <#list mostUsedTags as tag>
            <li>
                <a rel="alternate" href="" class="no-underline">
                </a>
                <a rel="tag" title="tag" href="">
                    ${tag.tag}(${tag.articlenum})
                </a>
            </li>
            </#list>
        </ul>
    </div>
    <div class="line"></div>
    </#if>


  <#--  <#if 0 != links?size>
    <div class="item">
        <div class="blog">
            <h4>友情链接</h4>
        </div>
        <ul id="sideLink" class="navi-tags">
            <#list links as link>
            <li>
                 <a rel="friend" href="${link.linkAddress}" title="${link.linkTitle}" target="_blank">
                    <img alt="${link.linkTitle}" 
                         src="${faviconAPI}<#list link.linkAddress?split('/') as x><#if x_index=2>${x}<#break></#if></#list>" width="16" height="16" /></a>
                <a rel="friend" href="${link.linkAddress}" title="${link.linkTitle}" target="_blank">
                    ${link.linkTitle}
                </a>
            </li>
            </#list>
        </ul>
    </div>
    <div class="line"></div>
    </#if> -->
   <#-- <#if 0 != archiveDates?size>
    <div class="item">
        <div class="archivio">
            <h4>存档</h4>
        </div>
        <ul id="save">
            <#list archiveDates as archiveDate>
            <li>
                <#if "en" == localeString?substring(0, 2)>
                <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                   title="${archiveDate.monthName} ${archiveDate.archiveDateYear}(${archiveDate.archiveDatePublishedArticleCount})">
                    ${archiveDate.monthName} ${archiveDate.archiveDateYear}</a>(${archiveDate.archiveDatePublishedArticleCount})
                <#else>
                <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                   title="${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}(${archiveDate.archiveDatePublishedArticleCount})">
                    ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}</a>(${archiveDate.archiveDatePublishedArticleCount})
                </#if>
            </li>
            </#list>
        </ul>
    </div>
    <div class="line"></div>
    </#if>-->
    <#--<#if 1 != users?size>
    <div class="item">
        <div class="side-author">
            <h4>${authorLabel}</h4>
        </div>
        <ul id="sideAuthor">
            <#list users as user>
            <li>
                <a href="${servePath}/authors/${user.oId}" title="${user.userName}">
                    ${user.userName}
                </a>
            </li>
            </#list>
        </ul>
    </div>
    </#if>-->
    <div class="rings" style="bottom: 0px;"></div>
</div>