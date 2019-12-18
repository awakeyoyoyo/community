
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
// (function ($) {
//     alert('start');
// function reply(fromId,commentId) {
//     console.log(111)
//     alert("1111")
//     $("#comment_btn").val("回复");
// }
// })(jQuery);
function clearReply(){
    var $comment_btn=$("#comment_btn");
    $comment_btn.text("评论");
    $comment_btn.removeClass("btn-warning");
    $("#clearReply").hide();
    $comment_btn.attr("onclick","postComment()");
    $("#Writetips").val("要评论点什么？");
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
    $("#content").val("");
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
};
