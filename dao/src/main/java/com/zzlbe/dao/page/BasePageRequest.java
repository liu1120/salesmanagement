package com.zzlbe.dao.page;

import lombok.Data;

/**
 * PROJECT: Sales
 * DESCRIPTION: 分页请求
 *
 * @author duGraceful
 * @date 2019/4/2
 */
@Data
public abstract class BasePageRequest {

    private Integer size = 20;

    private Integer page = 1;

    public Integer getFirstIndex() {
        return getSize() * (getPage() - 1);
    }

}
