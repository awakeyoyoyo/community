package com.awakeyo.community.mapper;

import com.awakeyo.community.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    Integer insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    User findByToken(String token);

    void updateTokenByAccoundId(@Param("token") String token, @Param("accountId") String accountId);

    Integer selectByaccoun_id(String accountId);

    User selectUserByaccoun_id(String fromUid);

    User selectByPhoneAndPassowrd(@Param("phone") String phone,@Param("password") String password);

    Integer updatePasswordByPhone(@Param("phone")String phone, @Param("newPassword")String newPassword);
}