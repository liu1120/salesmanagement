package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GoodstopicEntity {
    private long id;
    private long goodsid;
    private long createtime;
    private long content;
    private long uid;
    private long uname;
}
