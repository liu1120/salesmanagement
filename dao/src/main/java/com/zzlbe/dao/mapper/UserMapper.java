package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.search.UserSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    UserEntity userLogin(String name, String password);

    void insert(UserEntity userEntity);

    void update(UserEntity userEntity);

    UserEntity selectById(Long id);

    UserEntity selectByPhoneNo(String phoneNo);

    UserEntity selectByOpenid(String openId);

    List<UserEntity> selectByPage(UserSearch userSearch);

    Integer selectByPageTotal(UserSearch search);

    int userCount();

}
