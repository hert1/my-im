<div class="top">
    <div id="navigation">
        <a rel="nofollow" href="/" class="home">首页</a>
        <a href="/get_tags.bg" class="about">标签墙</a>
        <#--<#list pageNavigations as page>
        <a href="${page.pagePermalink}" <#if page.pageIcon != ''>style="background-image: url(${page.pageIcon})"</#if> target="${page.pageOpenTarget}" class="${page.pageTitle}">${page.pageTitle}</a>
        </#list>-->
        <a rel="alternate" href="/rss_xml.bg" class="classifiche">RSS</a>
        <a href="/search.bg">Search</a>
        <#if user??>
            <a  style="float: right" href="/admin/logout.bg">logout</a>
            <a  style="float: right" href="/get_h_blog.bg">${user.uname}</a>
        <#else>
            <a style="float: right" href="/static/admin/regise.html">Regise</a>
            <a style="float: right" href="/static/admin/login.html">Login</a>
        </#if>
    </div>
    <div class="thinks"></div>
</div>