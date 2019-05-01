package com.zzlbe.core.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AreaVO {
    private Long provincecode;
    private Long citycode;
    private Long countycode;
    private Long towncode;
}
