package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMapper {

    List<AddressEntity> selectByUid(long uid);//查询通过用户id查询

    void insert(AddressEntity addressEntity);//用户添加地址

    void update(AddressEntity addressEntity);//将地址设置为无效/有效
}
