package com.awakeyo.community.mapper;

import com.awakeyo.community.pojo.dto.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findByToken(String token);

    void updateTokenByAccoundId(@Param("token") String token, @Param("accountId") String accountId);

    int selectByaccoun_id(String accountId);

    User selectUserByaccoun_id(String fromUid);
}