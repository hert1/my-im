<#macro head title>
<meta charset="utf-8" />
<title>${title}</title>
<#nested>
<meta name="author" content="${blogTitle?html}" />
<meta name="generator" content="Solo" />
<meta name="owner" content="B3log Team" />
<meta name="revised" content="${blogTitle?html}, ${year}" />
<meta name="copyright" content="B3log" />
<meta http-equiv="Window-target" content="_top" />
<link type="text/css" rel="stylesheet" href="/static/css/favourite.css" charset="utf-8" />
<link type="text/css" rel="stylesheet" href="/static/css/favourite.min.css" charset="utf-8" />
<link href="${servePath}/rss.xml" title="RSS" type="application/rss+xml" rel="alternate" />
<link rel="icon" type="image/png" href="${servePath}/favicon.png" />
<link rel="search" type="application/opensearchdescription+xml" title="${title}" href="/opensearch.xml">
${htmlHead}
</#macro>