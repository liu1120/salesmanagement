package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMapper {

    AddressEntity selectById(long id);//通过id查询

    List<AddressEntity> selectByUid(long uid);//通过商品id查询评论

    void insert(AddressEntity addressEntity);//用户添加地址

    void update(AddressEntity addressEntity);//将地址设置为无效/有效
}
