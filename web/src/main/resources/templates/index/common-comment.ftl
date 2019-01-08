<div id="${comment.oId}" class="comment-body">
    <div class="comment-panel">
        <div class="left comment-author">
            <div>
                <img alt="头像" src="/static/images/twitter.png"/>
            </div>
        <#--<#if "http://" == comment.commentURL>-->
          <#--  <a class="left" title="${comment.user.uname}">${comment.user.uname}</a>-->
       <#-- <#else>-->
           <a href="" target="_blank" title="${comment.user.uname}">${comment.user.uname}</a>
        <#-- </#if>-->
        </div>
        <div class="left comment-info">
            <div class="left">
            ${comment.commentdate?string("yyyy-MM-dd HH:mm:ss")}
            <#if comment.isReply>

                <a href="${servePath}${article.permalink}#${comment.commentOriginalCommentId}"
                   onmouseover="page.showComment(this, '${comment.commentOriginalCommentId}', 3);"
                   onmouseout="page.hideComment('${comment.commentOriginalCommentId}')">${comment.commentOriginalCommentName}</a>
            </#if>
            </div>
        <#if article.commentable>
            <div class="right">
                <a rel="nofollow" class="no-underline"
                   href="javascript:replyTo('${comment.oId}');">${replyLabel}</a>
            </div>
        </#if>
            <div class="clear">
            </div>
            <div class="comment-content article-body">
            ${comment.commentBody}
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>