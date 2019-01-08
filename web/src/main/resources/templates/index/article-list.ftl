<#list articles as article>
<div class="article">
    <h2 class="article-title">
        <a rel="bookmark" class="no-underline" href="/get_article.bg?cid= ${article.cid}">
            ${article.articleTitle}
        </a>
        <#if article.hasUpdated>
        <sup class="red">
            已上传
        </sup>
        </#if>
        <#if article.articlePutTop>
        <sup class="red">
           置顶
        </sup>
        </#if>
    </h2>
    <div class="posttime-blue">
        <div class="posttime-MY">
            <#if article.hasUpdated>
            ${article.articleUpdateDate?string("yyyy-MM")}
            <#else>
            ${article.articleCreateDate?string("yyyy-MM")}
            </#if>
        </div>
        <div class="posttime-D">
            <#if article.hasUpdated>
            ${article.articleUpdateDate?string("dd")}
            <#else>
            ${article.articleCreateDate?string("dd")}
            </#if>
        </div>
    </div>
    <div class="article-abstract article-body">
        <div class="note">
            <div class="corner"></div>
            <div class="substance">
                ${article.description}
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="margin25">
        <a rel="nofollow" href="" class="left">
            <span class="left article-browserIcon" title="浏览次数"></span>
            <span class="count">${article.articleViewCount}</span>
        </a>
        <div class="left">
            <span class="tagsIcon" title="标签列表"></span>
             <#list article.tags?split(",") as articleTag>
            <span class="count">
                   <a rel="tag" href="">
                       ${articleTag}</a><#if articleTag_has_next>,</#if>
            </span>
            </#list>
        </div>
        <a rel="nofollow" href="" class="left">
            <span class="left articles-commentIcon" title="评论次数"></span>
            <span class="count">${article.articleCommentCount}</span>
        </a>
        <div class="right more">
            <a href="" class="right">
                阅读更多
            </a>
        </div>
        <div class="clear"></div>
    </div>
    <div class="footer">
        <div class="clear"></div>
    </div>
</div>
</#list>
<#--
<#if 0 != paginationPageCount>
<div class="pagination">
    <#if 1 != paginationPageNums?first>
    <a href="">第一页</a>
    <a id="previousPage" href="">上一页</a>
    </#if>
    <#list paginationPageNums as paginationPageNum>
    <#if paginationPageNum == paginationCurrentPageNum>
    <a href="${servePath}${path}?p=${paginationPageNum}" class="selected">${paginationPageNum}</a>
    <#else>
    <a href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
    </#if>
    </#list>
    <#if paginationPageNums?last != paginationPageCount>
    <a id="nextPage" href="${servePath}${path}?p=${paginationNextPageNum}">${nextPagePabel}</a>
    <a href="${servePath}${path}?p=${paginationPageCount}">${lastPageLabel}</a>
    </#if>
    &nbsp;&nbsp;${sumLabel} ${paginationPageCount} ${pageLabel}
</div>
</#if>-->
