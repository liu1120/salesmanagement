package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class GoodstopicEntity {
    private long id;
    private long goodsid;
    private Date createtime;
    private String content;
    private long uid;
    private String uname;
}
