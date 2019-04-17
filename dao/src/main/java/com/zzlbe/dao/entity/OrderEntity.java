package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
public class OrderEntity {
    private Long id;
    private Long goodsid;
    private Long num;
    private double price;
    private Long say;
    private Long sum;
    private Date datetime;
    private Long salespersonid;
    private Long userid;
    private int type;
    private Long logistics;
    private Long address;
    private int status;
    private String words;
    private String refuse;

}
