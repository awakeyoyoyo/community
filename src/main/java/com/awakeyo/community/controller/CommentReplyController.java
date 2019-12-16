package com.awakeyo.community.controller;

import com.awakeyo.community.common.ServerResponse;
import com.awakeyo.community.pojo.Comment;
import com.awakeyo.community.pojo.Reply;
import com.awakeyo.community.pojo.dto.User;
import com.awakeyo.community.service.CommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author awakeyoyoyo
 * @className CommentController
 * @description TODO
 * @date 2019-12-13 16:33
 */
@Controller
public class CommentReplyController {
    @Autowired
    private CommentReplyService commentReplyService;
    @PostMapping("/comment")
    @ResponseBody
    public ServerResponse docoment(Comment comment, HttpServletRequest request)
    {
        User user= (User)request.getSession().getAttribute("user");
        if (user==null){
            return ServerResponse.createByErrorMessage("NEED_LOGIN");
        }
        comment.setFromUid(user.getAccountId());
        return commentReplyService.writeComent(comment);
    }

    @PostMapping("/reply")
    @ResponseBody
    public ServerResponse doReply(Reply reply, HttpServletRequest request)
    {
//        User user= (User)request.getSession().getAttribute("user");
//        if (user==null){
//            return ServerResponse.createByErrorMessage("NEED_LOGIN");
//        }
//        reply.setFormUid(user.getAccountId());
        return commentReplyService.writeReply(reply);
    }

    @GetMapping("/comments/{type}/{topicId}")
    @ResponseBody
    public ServerResponse getCommentsReplyTopicId(@PathVariable("type") String type,
                                                  @PathVariable("topicId") Integer topId,
                                                  HttpServletRequest request){
//        User user= (User)request.getSession().getAttribute("user");
//        if (user==null){
//            return ServerResponse.createByErrorMessage("NEED_LOGIN");
//        }
        return commentReplyService.getCommentsReplyTopicId(type,topId);
    }
}
