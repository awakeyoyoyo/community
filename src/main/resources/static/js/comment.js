var t = null;
function sendcode() {
    var value=$("input[name='phone']").val();
    if(!(/^1[3456789]\d{9}$/.test(value))){
        alert("请输入正确手机号")
    }else {
        $.ajax({
            url: "http://blog.awakeyoyoyo.com/getCode?phone="+value,
            // url: "http://localhost:8080/getCode?phone="+value,
            type: "get",
            success: function (response) {
                if (response.status == 200) {
                } else {
                    alert("该手机已经注册过了。。。");
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
        url: "http://blog.awakeyoyoyo.com/comments/question/" + questionId,
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
function clearBlogReply(){
    var $comment_btn=$("#comment_btn");
    $comment_btn.text("评论");
    $comment_btn.removeClass("btn-warning");
    $("#clearReply").hide();
    $comment_btn.attr("onclick","postBlogComment()");
    $("#Writetips").val("要评论点什么？");
    $("#content").val("");
    $("#content").attr("placeholder","想对作者说些什么");
}
function clearQuestionReply(){
    var $comment_btn=$("#comment_btn");
    $comment_btn.text("评论");
    $comment_btn.removeClass("btn-warning");
    $("#clearReply").hide();
    $comment_btn.attr("onclick","postQuestionComment()");
    $("#Writetips").val("要评论点什么？");
    $("#content").val("");
    $("#content").attr("placeholder","想对作者说些什么");
}

function clearComment(){
    $("#Writetips").val("要评论点什么？");
    $("#content").val("");
    $("#content").attr("placeholder","想对作者说些什么");
}
function replyRecordReply(obj) {
    var $comment_btn=$("#comment_btn");
    var fromUserAID=obj.getAttribute("fromUserAId");
    var commentId=obj.getAttribute("commentId");
    $comment_btn.text("回复");
    $comment_btn.addClass("btn-warning");
    $("#clearReply").show();
    $("#Writetips").text("要回复点什么？");
    $("#content").attr("placeholder","想回复写什么");
    $comment_btn.attr("onclick","postReply("+fromUserAID+","+commentId+",\'record\')");
};
function replyQuestionReply(obj) {
    var $comment_btn=$("#comment_btn");
    var fromUserAID=obj.getAttribute("fromUserAId");
    var commentId=obj.getAttribute("commentId");
    $comment_btn.text("回复");
    $comment_btn.addClass("btn-warning");
    $("#clearReply").show();
    $("#Writetips").text("要回复点什么？");
    $("#content").attr("placeholder","想回复写什么");
    $comment_btn.attr("onclick","postReply("+fromUserAID+","+commentId+",\'question\')");
};
function replyBlogReply(obj) {
    var $comment_btn=$("#comment_btn");
    var fromUserAID=obj.getAttribute("fromUserAId");
    var commentId=obj.getAttribute("commentId");
    $comment_btn.text("回复");
    $comment_btn.addClass("btn-warning");
    $("#clearReply").show();
    $("#Writetips").text("要回复点什么？");
    $("#content").attr("placeholder","想回复写什么");
    $comment_btn.attr("onclick","postReply("+fromUserAID+","+commentId+",\'article\')");
};
function postReply(formUAID,commentId,type){
   // alert("postReply:"+formUAID+"-"+commentId)
    var questionId=$("#topicId").val();
    var content=$("#content").val();
    if (!content){
        alert("不能回复空内容")
        return
    }
    $.ajax({
        url:"http://blog.awakeyoyoyo.com/reply",
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
                if(type=="question"){
                    $("#comments").load("/comments/question/"+questionId)
                }
                if(type=="article"){
                    $("#comments").load("/comments/article/"+questionId)
                }
                if (type=="record"){
                    $("#comments").load("/comments/record/10086")
                }
            }
            else {
                // alert("fail");
                alert(response.msg);
            }
            // console.log(response);
        },

    })
    if(type=="article"){
        clearBlogReply();
    }else if (type=="record"){
        clearRecordReply()
    }
    else{
        clearQuestionReply();
    }
}
function ajaxBlog(obj) {
    var pageNo=obj.getAttribute("pageNo");
    $("#AticleBlog").load("/articles?pageNo="+pageNo+"&pageSize=3")
}
function ajaxBlogArticle(obj) {
    var pageNo=obj.getAttribute("pageNo");
    var tag=obj.getAttribute("tag")
    $("#categoryBlog").load("/ajaxcategories?tag="+tag+"&pageNo="+pageNo+"&pageSize=5")
}
function ajaxQuestion(obj) {
    var pageNo=obj.getAttribute("pageNo");
    $("#questions").load("/questions?pageNo="+pageNo+"&pageSize=5")
}
function postQuestionComment(){
    var questionId=$("#topicId").val();
    var content=$("#content").val();
    if (!content){
        alert("不能回复空内容")
        return
    }
    $.ajax({
        url:"http://blog.awakeyoyoyo.com/comment",
        type:"post",
        contentType:"application/x-www-form-urlencoded",
        data: {
            topicId: questionId,
            content: content,
            type:"question"
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
function postRecordComment(){
    var questionId=$("#topicId").val();
    var content=$("#content").val();
    if (!content){
        alert("不能回复空内容")
        return
    }
    $.ajax({
        url:"http://blog.awakeyoyoyo.com/comment",
        type:"post",
        contentType:"application/x-www-form-urlencoded",
        data: {
            topicId: questionId,
            content: content,
            type:"record"
        },
        success:function (response) {
            if (response.status==200) {
                // alert("success");
                $("#comments").load("/comments/record/10086")
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
function clearRecordReply(){
    var $comment_btn=$("#comment_btn");
    $comment_btn.text("评论");
    $comment_btn.removeClass("btn-warning");
    $("#clearReply").hide();
    $comment_btn.attr("onclick","postRecordComment()");
    $("#Writetips").val("要评论点什么？");
    $("#content").val("");
    $("#content").attr("placeholder","想对作者说些什么");
}
function postBlogComment(){
    var questionId=$("#topicId").val();
    var content=$("#content").val();
    if (!content){
        alert("不能回复空内容")
        return
    }
    $.ajax({
        url:"http://blog.awakeyoyoyo.com/comment",
        type:"post",
        contentType:"application/x-www-form-urlencoded",
        data: {
            topicId: questionId,
            content: content,
            type:"article"
        },
        success:function (response) {
            if (response.status==200) {
                // alert("success");
                $("#comments").load("/comments/article/"+questionId)
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