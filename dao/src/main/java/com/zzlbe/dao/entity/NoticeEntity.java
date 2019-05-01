package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
public class NoticeEntity {
    private Long id;
    private String title;
    private Integer type;
    private String content;
    private String sp_name;
    private Date time;
    private Long num;

}
