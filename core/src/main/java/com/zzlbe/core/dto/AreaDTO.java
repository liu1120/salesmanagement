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

    private String name;

    public AreaDTO(Long code, String name) {
        this.code = code;
        this.name = name;
        this.children = new HashMap<>();
        this.list = new ArrayList<>();
    }

    private Map<Long, AreaDTO> children;

    private List<AreaDTO> list;

}
