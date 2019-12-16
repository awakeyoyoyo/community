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
                updateComment(response.data);
            }
            else {
                alert("fail");
                alert(response.msg);
            }
            console.log(response);
        },

    })
    alert(content);
    alert(questionId);
}
function buildComentAndReply(Data) {
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
    // <div id="comment">
//         <div class="media" >
//         <div class="media-left">
//         <img class="media-object img-circle img-comment"
//     src="/2.jpg" alt="...">
//         </div>
//         <div class="media-body">
//         <h5 class="media-heading " style="margin-top: 5px">
//         <span ><P class="text-desc" >
//         <span class="text-import">lqhao</span>
//         <span>&nbsp&nbsp&nbsp</span>
//         <span >一天前</span>
//         </P></span>
//     </h5>
//     </div>
//     <span style="margin-left:30px">这里就是评论了</span>
//         </div>
}

function updateComment(data) {
    var questionId = data.topicId;
    var commentId=data.id;
    var fromUserName=data.fromUser.name;
    var fromUserAccountId=data.fromUser.accountId;
    var gmtCreate=data.gmtCreate;
    var $newElement=${}}

}
// $.ajax({
//     url:"http://localhost/8080/comments/question/",
//     type:"post",
//     data:{
//         topicId:"1",
//         content:'123',
//     }
// })


$('#btn_commit').click(function () {


})