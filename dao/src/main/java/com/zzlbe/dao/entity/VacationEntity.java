package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class VacationEntity {
    private long id;
    private long spid;
    private Date start;
    private Date end;
    private long days;
    private String reason;
    private int type;
    private int status;
    private String notes;

}
