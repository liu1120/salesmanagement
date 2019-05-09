package com.zzlbe.core.business.impl;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.response.UserInfoVO;
import com.zzlbe.core.util.UserUtils;
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.page.PageResponse;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 工具类
 *
 * @author amos
 * @date 2019/4/21
 */
public abstract class BaseBusinessImpl {

    /**
     * 分页工具方法（暂设为private，有需要可放开限制）
     *
     * @return PageResponse<T>
     */
    private static <T> PageResponse<T> pageResponse(List<T> entities, Integer total) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setSize(entities.size());
        pageResponse.setList(entities);
        pageResponse.setTotal(total);
        return pageResponse;
    }

    /**
     * 分页工具方法
     *
     * @return GenericResponse<PageResponse>
     */
    protected static <T> GenericResponse<PageResponse> genericPageResponse(List<T> entities, Integer total) {
        return new GenericResponse<>(pageResponse(entities, total));
    }

    /**
     * 构造用户信息
     *
     * @param userEntity UserEntity
     * @return UserInfoVO
     */
    UserInfoVO generateUserInfo(UserEntity userEntity) {
        String token = UserUtils.getUserToken(userEntity.getId());

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        BeanUtils.copyProperties(userEntity, userInfoVO);

        return userInfoVO;
    }

    GenericResponse<UserInfoVO> userInfoResponse(UserEntity userEntity) {
        return new GenericResponse<>(generateUserInfo(userEntity));
    }

}
