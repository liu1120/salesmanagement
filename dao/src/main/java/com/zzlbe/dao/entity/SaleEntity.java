package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SaleEntity {
    private Long id;
    private Long type;
    private Long discount;
    private Long start;
    private Long reach;
    private Long minus;
    private Date startime;
    private Date overtime;
    private Long areaids;
    private Long isOk;
}
