<script>
    window.onload = function () {
        if(typeof(Storage)=="undefined")
        {
            document.getElementById("result").innerHTML="对不起，您的浏览器不支持 web 存储。";
        }
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: '/index/index.bg',//url
            contentType: "application/json",
            success: function (result) {
                if (result.code == 100000) {
                    var data = result.data;
                    localStorage.setItem("index",data);
                    window.location.href="/index.bg";
                }
                else{
                    alert(result.mes)
                }
            }
        });
    }
</script>