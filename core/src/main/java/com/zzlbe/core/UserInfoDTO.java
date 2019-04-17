package com.zzlbe.core;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoDTO {
    private Long id;
    private String token;
    private String phoneNo;
}
