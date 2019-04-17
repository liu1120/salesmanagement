package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class GoodsEntity {
    private Long id;
    private String name;
    private String sort;
    private String version;
    private double price;
    private double minprice;
    private String introduce;
    private String newimgpath;
    private String point;
    private Date updatetime;
    private long isshow;
    private int num;
    private long credit;
}
