package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GoodssortEntity {
    private Long id;
    private String name;

}
