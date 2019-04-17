package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SuggesttopicEntity {
    private long id;
    private long suid;
    private Date time;
    private String content;
    private long uid;
    private String uname;
}
