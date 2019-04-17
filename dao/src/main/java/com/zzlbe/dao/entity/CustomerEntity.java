package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CustomerEntity {
    private long orid;
    private long uid;
    private long spid;
    private long goid;
    private long goodsnum;
    private int reason;
    private int type;
    private Date time;
    private long descrition;
    private double money;
}
