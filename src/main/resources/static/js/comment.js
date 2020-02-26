var t = null;
function sendcode() {
    var value=$("input[name='phone']").val();
    if(!(/^1[3456789]\d{9}$/.test(value))){
        alert("请输入正确手机号")
    }else {
        $.ajax({
            url: "http://localhost:8080/getCode?phone="+value,
            type: "get",
            success: function (response) {
                if (response.status == 200) {
                } else {
                    alert("发送失败，服务器可能炸了请稍后再试试");
                }
                console.log(response);
            },
        })
        btnCheck();
    }


}
function btnCheck() {
    var time = 60;
    $("#GetCode").attr("disabled", "disabled");
    var timer = setInterval(function () {
        if (time == 0) {
            clearInterval(timer);
            $("#GetCode").val("获取验证码");
        } else {
            $("#GetCode").val(time + "秒后可重新发送");
            time--;
        }
    }, 1000);
}
function time(creatTime)
{
    clearTimeout(t);//清除定时器
    dt = new Date();
    dt=dt-creatTime;
    var y=dt.getFullYear();
    var d=dt.getDay();
    var h=dt.getHours();//获取时
    var m=dt.getMinutes();//获取分
    var s=dt.getSeconds();//获取秒
    document.getElementById("showTime").innerHTML =  "本站已经存活了："+h+"时"+m+"分"+s+"秒";
    t = setTimeout(time,1000); //设定定时器，循环运行
}
function initFooterTime() {
//todo
}
function initComentAndReply() {
    var questionId=$("#topicId").val();
    $.ajax({
        url: "http://localhost:8080/comments/question/" + questionId,
        type: "get",
        success: function (response) {
            if (response.status == 200) {
                var data = response.data;
                buildComentAndReply(data);
            } else {
                alert("fail");
                alert(response.msg);
            }
            console.log(response);
        },
    })
};
function clearReply(){
    var $comment_btn=$("#comment_btn");
    $comment_btn.text("评论");
    $comment_btn.removeClass("btn-warning");
    $("#clearReply").hide();
    $comment_btn.attr("onclick","postComment()");
    $("#Writetips").val("要评论点什么？");
    $("#content").val("");
    $("#content").attr("placeholder","想对作者说些什么");
}

function clearComment(){
    $("#Writetips").val("要评论点什么？");
    $("#content").val("");
    $("#content").attr("placeholder","想对作者说些什么");
}
function replyReply(obj) {
    var $comment_btn=$("#comment_btn");
    var fromUserAID=obj.getAttribute("fromUserAId");
    var commentId=obj.getAttribute("commentId");
    $comment_btn.text("回复");
    $comment_btn.addClass("btn-warning");
    $("#clearReply").show();
    $("#Writetips").text("要回复点什么？");
    $("#content").attr("placeholder","想回复写什么");
    $comment_btn.attr("onclick","postReply("+fromUserAID+","+commentId+")");
};
function postReply(formUAID,commentId){
   // alert("postReply:"+formUAID+"-"+commentId)
    var questionId=$("#topicId").val();
    var content=$("#content").val();
    if (!content){
        alert("不能回复空内容")
        return
    }
    $.ajax({
        url:"http://localhost:8080/reply",
        type:"post",
        contentType:"application/x-www-form-urlencoded",
        data: {
            toUid: formUAID,
            content: content,
            commentId:commentId,
        },
        success:function (response) {
            if (response.status==200) {
                // alert("success");
                $("#comments").load("/comments/question/"+questionId)
            }
            else {
                // alert("fail");
                alert(response.msg);
            }
            // console.log(response);
        },

    })
    clearReply();
}
function postComment(){
    var questionId=$("#topicId").val();
    var content=$("#content").val();
    if (!content){
        alert("不能回复空内容")
        return
    }
    $.ajax({
        url:"http://localhost:8080/comment",
        type:"post",
        contentType:"application/x-www-form-urlencoded",
        data: {
            topicId: questionId,
            content: content,
        },
        success:function (response) {
            if (response.status==200) {
                // alert("success");
                $("#comments").load("/comments/question/"+questionId)
            }
            else {
                // alert("fail");
                alert(response.msg);
            }
            console.log(response);
        },

    })
    clearComment();
};


function SelectTag(obj){
    var previous=$("#tag").val();
    value=obj.getAttribute("data-tag");
    if (previous.indexOf(value)!=-1){
        alert("已经添加了 别再点啦")
    }else {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag() {
    $('li[name="tabs"]').eq(0).addClass('active');
    $('div[name="tags"]').eq(0).addClass('active');
    $("#SelectTag").show();
}

function closeSelectTag() {
    $("#SelectTag").hide();
}