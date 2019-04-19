package com.zzlbe.core.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SellerVO {
    private Long id;
    private String username;
    private String password;
}
