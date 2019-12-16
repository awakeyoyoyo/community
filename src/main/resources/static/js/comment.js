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
                //todo
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
//todo
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
}

function updateComment(data) {
    //todo
    var questionId = data.topicId;
    var commentId=data.id;
    var fromUserName=data.fromUser.name;
    var fromUserAccountId=data.fromUser.accountId;
    var gmtCreate=data.gmtCreate;

}


function reply(obj) {
    
}