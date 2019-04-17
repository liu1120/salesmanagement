package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SentgiftEntity {

    private long id;
    private long fromid;
    private long toid;
    private String tophone;
    private String address;
    private int type;
    private long logistics;
    private long num;
    private long credit;
    private int status;
    private Date date;
}
