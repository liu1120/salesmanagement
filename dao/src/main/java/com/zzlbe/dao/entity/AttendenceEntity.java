package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class AttendenceEntity {
    private long id;
    private long spid;
    private Date day;
    private int type;
    private Date start;
    private int startType;
    private Date end;
    private int endType;

}
