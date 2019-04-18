package com.zzlbe.dao.page;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 分页响应
 *
 * @author duGraceful
 * @date 2019/4/2
 */
@Data
@Accessors(chain = true)
public class PageResponse<T> {

    private Integer size;

    private List<T> list;

}
