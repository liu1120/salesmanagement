package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SaleEntity {
    private long id;
    private long type;
    private long discount;
    private long start;
    private long reach;
    private long minus;
    private Date startime;
    private Date overtime;
    private long areaids;
    private long isOk;
}
