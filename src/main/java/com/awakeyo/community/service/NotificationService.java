package com.awakeyo.community.service;

import com.awakeyo.community.common.NotificationTypeEnum;
import com.awakeyo.community.mapper.NotificationMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.Notification;
import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.dto.NotificationDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author awakeyoyoyo
 * @className NotificationService
 * @description TODO
 * @date 2020-01-30 16:31
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    public PageResult<NotificationDTO> getListByUserid(Integer id, Integer pageNo, Integer pageSize) {
        Integer itemCount=notificationMapper.selectAll(id);
        int  pageCount;
        if (itemCount/pageSize==0){
            pageCount=1;
        }else if (itemCount%pageSize==0){
            pageCount=itemCount/pageSize;
        }else {
            pageCount=itemCount/pageSize+1;
        }
        if (pageNo<1){
            pageNo=1;
        }
        if (pageNo>pageCount){
            pageNo=pageCount;
        }
        Integer pageBegin=pageSize*(pageNo-1);
        List<Notification> notifications=notificationMapper.selectListByUser(id,pageBegin,pageSize);
        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        PageResult<NotificationDTO> pageResult=new PageResult<>();
        if (notifications.size()==0){
            pageResult.init(pageCount,pageNo);
            return  pageResult;
        }
        for (Notification n: notifications) {
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(n,notificationDTO);
            if (n.getType()== NotificationTypeEnum.REPLY_COMMENT.getType()) {
                notificationDTO.setType(NotificationTypeEnum.REPLY_COMMENT.getName());
            }else if (n.getType()==NotificationTypeEnum.REPLY_QUESTION.getType()){
                notificationDTO.setType(NotificationTypeEnum.REPLY_QUESTION.getName());
            }else {
                notificationDTO.setType(NotificationTypeEnum.REPLY_BLOG.getName());
            }
            notificationDTOS.add(notificationDTO);
        }
        pageResult.init(pageCount,pageNo);
        pageResult.setReslts(notificationDTOS);
        return pageResult;
    }

    public Long getUnreadCount(Integer id) {
        return notificationMapper.selectByUserIdUnread(id);
    }

    public void read(@Param("id") Integer id, @Param("userId") Integer userId) {
         notificationMapper.readNotification(id,userId);
    }
//    public List<Notification> getNotifilerByUserid(Integer id) {
//        List<Notification> notifications=notificationMapper.selectListByUserStatus(id,pageBegin,pageSize);
//
//    }
}
