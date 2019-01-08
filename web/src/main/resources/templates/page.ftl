<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-2018, b3log.org & hacpai.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

-->
<#include "index/macro-head.ftl">
<#include "index/macro-comments.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${page.pageTitle} - ${blogTitle}">
        <meta name="keywords" content="${metaKeywords},${page.pageTitle}" />
        <meta name="description" content="${metaDescription}" />
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
                        <div class="article">
                            <div class="article-abstract">
                                <div class="note">
                                    <div class="corner"></div>
                                    <div class="substance article-body">
                                        ${page.pageContent}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <@comments commentList=pageComments article=page></@comments>
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
        <@comment_script oId=page.oId></@comment_script>
    </body>
</html>
