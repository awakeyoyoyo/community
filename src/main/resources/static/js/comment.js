
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
    $("#comment_btn").text("评论");
    $("#comment_btn").removeClass("btn-warning");
    $("#clearReply").hide();
}
function replyReply(obj) {

    alert(obj.getAttribute("fromUserAId"))
    $("#comment_btn").text("回复");
    $("#comment_btn").addClass("btn-warning");
    $("#clearReply").show();
};

function postComment(){
    var questionId=$("#topicId").val();
    var content=$("#content").val();
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
                alert("success");
                $("#comments").load("/comments/question/"+questionId)
            }
            else {
                alert("fail");
                alert(response.msg);
            }
            console.log(response);
        },

    })
};
