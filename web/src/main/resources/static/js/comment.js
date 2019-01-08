function submitComment(fid, uid) {
    var body = $("#comment").val();
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: '/comment.bg',//url
        contentType: "application/json",
        data: JSON.stringify({body: body, fid: fid, uid: uid}),
        success: function (result) {
            if (result.code == 100000) {
                alert("回复成功");
                location.reload();
            }
            else if (result.code==100001) {
                alert("参数异常")
            }
            else if (result.code==100002) {
                alert("请登录");
                window.location.href="/admin/login.bg";
            }
           else{
                alert(result.mes)
            }
                }
    });
}

function login(){
    var uname = $("#id_username").val();
    var upwd = $("#id_password").val();
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: '/admin/login.bg',//url
        contentType: "application/json",
        data: JSON.stringify({uname: uname, upwd: upwd}),
        success: function (result) {
            if (result.code == 100000) {
                window.location.href="/index.bg";
            }
            else if (result.code==100001) {
                alert("参数异常")
            }
            else{
                alert(result.mes)
            }
        }
    });
}
function regise(){
    var uname = $("#id_username").val();
    var upwd = $("#id_password").val();
    var cupwd = $("#id_confirm_password").val();

    if(upwd!=cupwd){
        alert("两次密码输入不同！");
    }else{
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: '/admin/regise.bg',//url
        contentType: "application/json",
        data: JSON.stringify({uname: uname, upwd: upwd}),
        success: function (result) {
            if (result.code == 100000) {
                alert(222222);
                window.location.href="/admin/login.bg";
            }
            else if (result.code==100001) {
                alert("参数异常")
            }
            else{
                alert(result.mes)
            }
        }
    });}
}