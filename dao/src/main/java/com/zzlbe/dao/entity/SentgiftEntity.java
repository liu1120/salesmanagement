package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SentgiftEntity {

    private Long id;
    private Long fromid;
    private Long toid;
    private String tophone;
    private String address;
    private Integer type;
    private Long logistics;
    private Long num;
    private Long credit;
    private Integer status;
    private Date date;
}
