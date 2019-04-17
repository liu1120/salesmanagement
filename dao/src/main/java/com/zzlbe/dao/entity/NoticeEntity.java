package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
public class NoticeEntity {
    private long id;
    private String title;
    private int type;
    private String content;
    private String sp_name;
    private Date time;
    private long num;

}
