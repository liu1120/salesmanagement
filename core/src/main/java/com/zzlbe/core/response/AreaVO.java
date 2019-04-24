package com.zzlbe.core.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AreaVO {
    private long provincecode;
    private String provincename;
    private long citycode;
    private String cityname;
    private long countycode;
    private String countyname;
    private long towncode;
    private String townname;
}
