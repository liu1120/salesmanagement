package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SaleEntity {
    private long id;
    private long type;
    private long discount;
    private long start;
    private long reach;
    private long minus;
    private long startime;
    private long overtime;
    private long areaids;
    private long isOk;

}
