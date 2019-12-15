package com.awakeyo.community.service;

import com.awakeyo.community.common.ResponseCode;
import com.awakeyo.community.common.ServerResponse;
import com.awakeyo.community.pojo.Comment;
import com.awakeyo.community.mapper.CommentMapper;
import com.awakeyo.community.mapper.ReplyMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.Reply;
import com.awakeyo.community.pojo.dto.CommentDTO;
import com.awakeyo.community.pojo.dto.ReplyDTO;
import com.awakeyo.community.pojo.dto.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author awakeyoyoyo
 * @className CommentService
 * @description TODO
 * @date 2019-12-13 16:35
 */
@Service
public class CommentReplyService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;

    public ServerResponse writeComent(Comment comment) {
        if (comment.getContent()==null||comment.getContent().trim().isEmpty()){
            return ServerResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }

        int row =userMapper.selectByaccoun_id(comment.getFromUid());
        if (row <0) {
            return ServerResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        //写评论。
        comment.setGmtCreate(new Date().getTime());
        comment.setCommentLike(0);
        row=commentMapper.insertSelective(comment);
        if (row<0){
            return ServerResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        CommentDTO commentDTO=new CommentDTO();
        BeanUtils.copyProperties(comment,commentDTO);
        User user =userMapper.selectUserByaccoun_id(comment.getFromUid());
        commentDTO.setFromUser(user);
        return ServerResponse.createBySuccess(commentDTO);
    }

    public ServerResponse writeReply(Reply reply) {
        //todo 数据校验
        if (reply.getContent()==null||reply.getContent().trim().isEmpty()){
            return ServerResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        if (reply.getCommentId()==null){
            return ServerResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        User formUser =userMapper.selectUserByaccoun_id(reply.getFormUid());
        User toUser =userMapper.selectUserByaccoun_id(reply.getToUid());
        if (formUser== null||toUser==null) {
            return ServerResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        //回复
        reply.setReplyLike(0);
        reply.setGmtCreate(new Date().getTime());
        replyMapper.insert(reply);
        ReplyDTO replyDTO=new ReplyDTO();
        BeanUtils.copyProperties(reply,replyDTO);
        replyDTO.setFromUser(formUser);
        replyDTO.setToUser(toUser);
        return ServerResponse.createBySuccess(replyDTO);
    }

    public ServerResponse getCommentsReplyTopicId(String type, Integer topId) {
        if ("question".equals(type)){
            List<Comment> comments=commentMapper.selectByTopId(topId);
            List<CommentDTO> commentDTOS=new ArrayList<>();
            for (Comment comment:comments) {
                CommentDTO commentDTO=initCommentDTO(comment);
                commentDTOS.add(commentDTO);
            }
            return ServerResponse.createBySuccess(commentDTOS);
        }
        else {
            return ServerResponse.createByError();
        }
    }

    private CommentDTO initCommentDTO(Comment comment){
        CommentDTO commentDTO=new CommentDTO();
        BeanUtils.copyProperties(comment,commentDTO);
        User user =userMapper.selectUserByaccoun_id(comment.getFromUid());
        commentDTO.setFromUser(user);
        List<Reply> replies=replyMapper.selectByCommentId(comment.getId());
        List<ReplyDTO> replyDTOS=new ArrayList<>();
        if (replies.isEmpty()){
            return commentDTO;
        }
        else {
            for (Reply r :replies) {
               ReplyDTO replyDTO= initReplyDTO(r);
               replyDTOS.add(replyDTO);
            }
        }
        commentDTO.setReplyDTOS(replyDTOS);
        return commentDTO;
    }

    private ReplyDTO initReplyDTO(Reply reply){
        ReplyDTO replyDTO=new ReplyDTO();
        BeanUtils.copyProperties(reply,replyDTO);
        User formUser =userMapper.selectUserByaccoun_id(reply.getFormUid());
        User toUser =userMapper.selectUserByaccoun_id(reply.getToUid());
        replyDTO.setFromUser(formUser);
        replyDTO.setToUser(toUser);
        return replyDTO;
    }
}
