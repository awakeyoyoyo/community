package com.awakeyo.community.mapper;
import com.awakeyo.community.pojo.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);

    List<Notification> selectListByUser(@Param("userId") Integer userId, @Param("pageBegin") Integer pageBegin, @Param("pageSize") Integer pageSize);

    Integer selectAll(Integer id);
}