package com.awakeyo.community.controller;

import com.awakeyo.community.common.ServerResponse;
import com.awakeyo.community.exception.CustomizeException;
import com.awakeyo.community.pojo.Comment;
import com.awakeyo.community.pojo.Reply;
import com.awakeyo.community.pojo.dto.CommentDTO;
import com.awakeyo.community.pojo.dto.QuestionDTO;
import com.awakeyo.community.pojo.dto.User;
import com.awakeyo.community.service.CommentReplyService;
import com.awakeyo.community.service.QustionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Autowired
    private QustionService qustionService;
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
    public String getCommentsReplyTopicId(@PathVariable("type") String type,
                                                  @PathVariable("topicId") Integer topId,
                                                  HttpServletRequest request,
                                          Model model){
//        User user= (User)request.getSession().getAttribute("user");
//        if (user==null){
//            return ServerResponse.createByErrorMessage("NEED_LOGIN");
//        }
        QuestionDTO questionDTO=qustionService.getByIdAndIncView(topId);
        if (questionDTO==null){
            throw new CustomizeException("别乱调戏接口-。-");
        }
        model.addAttribute("question",questionDTO);
        return "question::comments";
    }
}
