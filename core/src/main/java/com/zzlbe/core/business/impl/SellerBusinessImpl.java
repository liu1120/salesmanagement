package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.SellerBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.request.UserInfoModifyForm;
import com.zzlbe.core.request.UserPasswordModifyForm;
import com.zzlbe.core.response.UserInfoVO;
import com.zzlbe.core.util.CheckUtil;
import com.zzlbe.core.util.UserUtils;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.mapper.SellerMapper;
import com.zzlbe.dao.page.PageResponse;
import com.zzlbe.dao.search.SellerSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duGraceful
 */
@Component("sellerBusiness")
public class SellerBusinessImpl implements SellerBusiness {

    @Resource
    private SellerMapper sellerMapper;


    @Override
    public GenericResponse register(RegisterForm registerForm) {
        if (!CheckUtil.isPhoneNo(registerForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        SellerEntity sellerEntity = sellerMapper.selectByPhoneNo(registerForm.getPhone());
        if (sellerEntity != null) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_HAS_REGISTERED);
        }

        sellerEntity = new SellerEntity();
        BeanUtils.copyProperties(registerForm, sellerEntity);
        sellerMapper.insert(sellerEntity);

        return userInfoResponse(sellerEntity);
    }

    @Override
    public GenericResponse login(LoginForm loginForm) {
        if (!CheckUtil.isPhoneNo(loginForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        SellerEntity sellerEntity = sellerMapper.selectByPhoneNo(loginForm.getPhone());
        if (sellerEntity == null) {
            return new GenericResponse(ErrorCodeEnum.SELLER_NOT_FOUND);
        }
        if (!sellerEntity.getPassword().equals(loginForm.getPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_LOGIN_PASSWORD_ERROR);
        }

        return userInfoResponse(sellerEntity);
    }

    @Override
    public GenericResponse modify(UserInfoModifyForm modifyForm) {
        if (!CheckUtil.isPhoneNo(modifyForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        SellerEntity sellerEntity = sellerMapper.selectByPhoneNo(modifyForm.getPhone());
        if (sellerEntity != null && !sellerEntity.getId().equals(modifyForm.getId())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PHONE_HAS_REGISTERED);
        }

        sellerEntity = sellerMapper.selectById(modifyForm.getId());
        if (sellerEntity == null) {
            return new GenericResponse(ErrorCodeEnum.SELLER_NOT_FOUND);
        }

        BeanUtils.copyProperties(modifyForm, sellerEntity);
        sellerMapper.update(sellerEntity);

        return userInfoResponse(sellerEntity);
    }

    @Override
    public GenericResponse passwordModify(UserPasswordModifyForm modifyForm) {
        if (!modifyForm.getNewPassword().equals(modifyForm.getEnterPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PASSWORD_ENTER_ERROR);
        }
        SellerEntity sellerEntity = sellerMapper.selectById(modifyForm.getId());
        if (sellerEntity == null) {
            return new GenericResponse(ErrorCodeEnum.SELLER_NOT_FOUND);
        }
        if (!sellerEntity.getPassword().equals(modifyForm.getOldPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PASSWORD_ERROR);
        }

        sellerEntity.setPassword(modifyForm.getEnterPassword());
        sellerMapper.update(sellerEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse userInfo(UserInfoDTO userInfoDTO) {
        SellerEntity sellerEntity = null;
        if (userInfoDTO.getId() != null) {
            sellerEntity = sellerMapper.selectById(userInfoDTO.getId());
        } else if (userInfoDTO.getPhone() != null) {
            sellerEntity = sellerMapper.selectByPhoneNo(userInfoDTO.getPhone());
        } else if (userInfoDTO.getToken() != null) {
            sellerEntity = sellerMapper.selectById(UserUtils.getUserId(userInfoDTO.getToken()));
        }

        if (sellerEntity == null) {
            return new GenericResponse(ErrorCodeEnum.SELLER_NOT_FOUND);
        }

        return userInfoResponse(sellerEntity);
    }

    @Override
    public GenericResponse userAll(SellerSearch sellerSearch) {
        List<UserInfoVO> userInfoVOS = new ArrayList<>();
        List<SellerEntity> userEntities = sellerMapper.selectByPage(sellerSearch);
        Integer total = sellerMapper.selectByPageTotal(sellerSearch);
        if (userEntities != null) {
            userEntities.forEach(sellerEntity -> userInfoVOS.add(generateUserInfo(sellerEntity)));
        }
        PageResponse<UserInfoVO> pageResponse = new PageResponse<>();
        pageResponse.setSize(userInfoVOS.size());
        pageResponse.setList(userInfoVOS);
        pageResponse.setTotal(total);

        return new GenericResponse<>(pageResponse);
    }

    /**
     * 构造用户信息
     *
     * @param sellerEntity sellerEntity
     * @return UserInfoVO
     */
    private UserInfoVO generateUserInfo(SellerEntity sellerEntity) {
        String token = UserUtils.getUserToken(sellerEntity.getId());

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        BeanUtils.copyProperties(sellerEntity, userInfoVO);

        return userInfoVO;
    }

    private GenericResponse<UserInfoVO> userInfoResponse(SellerEntity sellerEntity) {
        return new GenericResponse<>(generateUserInfo(sellerEntity));
    }

}
