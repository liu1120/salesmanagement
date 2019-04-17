package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TripEntity {
    private long id;
    private String city;
    private String tocity;
    private String type;
    private String intent;
    private Date start;
    private Date stop;
    private long spid;
    private int state;
    private String reason;

}
