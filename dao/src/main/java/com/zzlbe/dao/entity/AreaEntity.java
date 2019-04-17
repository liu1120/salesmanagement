package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AreaEntity {
    private long id;
    private long provincecode;
    private String provincename;
    private long citycode;
    private String cityname;
    private long countycode;
    private String countyname;
    private long towncode;
    private String townname;
    private long spid;
}
