package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AreaEntity {
    private Long id;
    private Long provincecode;
    private String provincename;
    private Long citycode;
    private String cityname;
    private Long countycode;
    private String countyname;
    private Long towncode;
    private String townname;
    private Long spid;
}
