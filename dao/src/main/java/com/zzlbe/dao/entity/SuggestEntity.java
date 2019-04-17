package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SuggestEntity  {
    private long id;
    private int type;
    private String title;
    private String content;
    private long uid;
    private String goodsname;
    private long spid;
    private Date time;
    private int isok;

}
