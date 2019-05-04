package com.zzlbe.core.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PROJECT: Sales
 * DESCRIPTION: 地区格式化JSON
 *
 * @author amos
 * @date 2019/5/1
 */
@Data
public class AreaDTO {

    private Long code;

    private String city;

    public AreaDTO(Long code, String city) {
        this.code = code;
        this.city = city;
        this.childrenMap = new HashMap<>();
        this.children = new ArrayList<>();
    }

    private Map<Long, AreaDTO> childrenMap;

    private List<AreaDTO> children;

}
