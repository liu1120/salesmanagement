package com.zzlbe.core.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AreaVO {
    private long provincecode;
    private long citycode;
    private long countycode;
    private long towncode;
}
