package com.awakeyo.community.service;

import com.awakeyo.community.common.NotificationStatusEnum;
import com.awakeyo.community.common.NotificationTypeEnum;
import com.awakeyo.community.common.ResponseCode;
import com.awakeyo.community.common.WebResponse;
import com.awakeyo.community.mapper.*;
import com.awakeyo.community.pojo.*;
import com.awakeyo.community.pojo.dto.CommentDTO;
import com.awakeyo.community.pojo.dto.ReplyDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Transactional
    public WebResponse writeComent(Comment comment) {
        //todo 枚举类来处理异常消息
        if (comment.getContent()==null||comment.getContent().trim().isEmpty()){
            return WebResponse.createByErrorMessage("都没有填写回复内容呢");
        }
        int row =userMapper.selectByaccoun_id(comment.getFromUid());
        if (row <0) {
            return WebResponse.createByErrorMessage("别乱调用接口。。");
        }
        if (comment.getType().equals("question")) {
            Question question = questionMapper.selectByPrimaryKey(comment.getTopicId());
            if (question==null){
                return WebResponse.createByErrorMessage("没有这个提问，别乱调用接口");
            }
            Integer rowCount=doComment(comment);
            if (rowCount<0){
                return WebResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
            }
            //写评论。
            questionMapper.updateReplyCountByTopicId(comment.getTopicId());
            CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            User user =userMapper.selectUserByaccoun_id(comment.getFromUid());
            commentDTO.setFromUser(user);
            //发送通知
            createCommentNotify(question, user);
            return WebResponse.createBySuccess();
        }
        else if(comment.getType().equals("record")){
            Integer rowCount=doComment(comment);
            if (rowCount<0){
                return WebResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
            }
            return WebResponse.createBySuccess();

        }
        else{
            Article article=articleMapper.selectByPrimaryKey(comment.getTopicId());
            if (article==null){
                return WebResponse.createByErrorMessage("没有这篇文章，别乱调用接口");
            }
            Integer rowCount=doComment(comment);
            if (rowCount<0){
                return WebResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
            }
            articleMapper.updateReplyCountByTopicId(comment.getTopicId());
            CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            User user =userMapper.selectUserByaccoun_id(comment.getFromUid());
            commentDTO.setFromUser(user);
            //发送通知
            createBlogCommentNotify(article, user);
            return WebResponse.createBySuccess();
        }
    }
    public Integer doComment(Comment comment){
        comment.setGmtCreate(new Date().getTime());
        comment.setCommentLike(0);
        Integer rowCount=commentMapper.insert(comment);
        return rowCount;
    }
    private void createBlogCommentNotify(Article article, User user) {
        Notification notification=new Notification();
        notification.setGmtCreate(new Date().getTime());
        notification.setType(NotificationTypeEnum.REPLY_BLOG.getType());
        //问题的id
        notification.setOuterid(article.getId());
        //写通知的人
        notification.setNotifier(user.getId());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        //接受通知的人
        notification.setReciver(article.getCreator());
        notification.setOuterTitle(article.getTitle());
        notification.setNotifierName(user.getName());
        notificationMapper.insert(notification);
    }
    private void createCommentNotify(Question quesction, User user) {
        Notification notification=new Notification();
        notification.setGmtCreate(new Date().getTime());
        notification.setType(NotificationTypeEnum.REPLY_QUESTION.getType());
        //问题的id
        notification.setOuterid(quesction.getId());
        //写通知的人
        notification.setNotifier(user.getId());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        //接受通知的人
        notification.setReciver(quesction.getCreator());
        notification.setOuterTitle(quesction.getTitle());
        notification.setNotifierName(user.getName());
        notificationMapper.insert(notification);
    }

    @Transactional
    public WebResponse writeReply(Reply reply) {
        //todo 数据校验
        if (reply.getContent()==null||reply.getContent().trim().isEmpty()){
            return WebResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        if (reply.getCommentId()==null){
            return WebResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        User formUser =userMapper.selectUserByaccoun_id(reply.getFormUid());
        User toUser =userMapper.selectUserByaccoun_id(reply.getToUid());
        if (formUser== null||toUser==null) {
            return WebResponse.createByErrorMessage(ResponseCode.ERROR.getDesc());
        }
        //回复
        reply.setReplyLike(0);
        reply.setGmtCreate(new Date().getTime());
        replyMapper.insert(reply);
        ReplyDTO replyDTO=new ReplyDTO();
        BeanUtils.copyProperties(reply,replyDTO);
        replyDTO.setFromUser(formUser);
        replyDTO.setToUser(toUser);
        //发送通知
        Long commentId=reply.getCommentId();
        Comment comment=commentMapper.selectByPrimaryKey(commentId);
        createReplyNotify(reply.getContent(),formUser,comment.getTopicId(), NotificationTypeEnum.REPLY_COMMENT, toUser.getId());
        return WebResponse.createBySuccess(replyDTO);
    }

    private void createReplyNotify(String outterTitle,User formUser, Integer questionId, NotificationTypeEnum replyComment, Integer id) {
        Notification notification = new Notification();
        notification.setGmtCreate(new Date().getTime());
        notification.setType(replyComment.getType());
        notification.setOuterid(questionId);
        notification.setNotifier(formUser.getId());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReciver(id);
        notification.setNotifierName(formUser.getName());
        notification.setOuterTitle(outterTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> getCommentsReplyTopicId(String type, Integer topId) {
        List<Comment> comments=null;
        if ("question".equals(type)){
            comments=commentMapper.selectByTopIdType(topId,type);
            List<CommentDTO> commentDTOS=new ArrayList<>();
            for (Comment comment:comments) {
                CommentDTO commentDTO=initCommentDTO(comment);
                commentDTOS.add(commentDTO);
            }
            return commentDTOS;
        }
        else if ("article".equals(type)){
            comments=commentMapper.selectByTopIdType(topId,type);
            List<CommentDTO> commentDTOS=new ArrayList<>();
            for (Comment comment:comments) {
                CommentDTO commentDTO=initCommentDTO(comment);
                commentDTOS.add(commentDTO);
            }
            return commentDTOS;
        }
        else if ("record".equals(type)){
            comments=commentMapper.selectByTopIdType(topId,type);
            List<CommentDTO> commentDTOS=new ArrayList<>();
            for (Comment comment:comments) {
                CommentDTO commentDTO=initCommentDTO(comment);
                commentDTOS.add(commentDTO);
            }
            return commentDTOS;
        }
        else {
            return null;
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
