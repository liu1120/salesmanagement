package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class GoodstopicEntity {
    private Long id;
    private Long goodsid;
    private Date createtime;
    private String content;
    private Long uid;
    private String uname;
}
