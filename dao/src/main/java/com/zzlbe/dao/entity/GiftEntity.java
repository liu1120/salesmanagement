package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class GiftEntity {
    private Long id;
    private String name;
    private String describe;
    private Long credit;
    private Long num;
    private int isshow;
    private Date endtime;
    private String imgpath;
}
