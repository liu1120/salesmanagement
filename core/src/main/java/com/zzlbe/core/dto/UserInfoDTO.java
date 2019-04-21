package com.zzlbe.core.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class UserInfoDTO {
    private Long id;
    private String token;
    private String phone;
}
