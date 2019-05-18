package com.zzlbe.core.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.zzlbe.core.business.UserBusiness;
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
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.mapper.OrderMapper;
import com.zzlbe.dao.mapper.SentgiftMapper;
import com.zzlbe.dao.mapper.UserMapper;
import com.zzlbe.dao.page.PageResponse;
import com.zzlbe.dao.search.CreditConsumeBySendGiftVO;
import com.zzlbe.dao.search.CreditGetByOrderVO;
import com.zzlbe.dao.search.UserSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("userBusiness")
public class UserBusinessImpl extends BaseBusinessImpl implements UserBusiness {

    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SentgiftMapper sentgiftMapper;

    @Override
    public GenericResponse register(RegisterForm registerForm) {
        if (!CheckUtil.isPhoneNo(registerForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(registerForm.getPhone());
        if (userEntity != null) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_HAS_REGISTERED);
        }

        userEntity = new UserEntity();
        BeanUtils.copyProperties(registerForm, userEntity);
        userMapper.insert(userEntity);

        return userInfoResponse(userEntity);
    }

    @Override
    public GenericResponse login(LoginForm loginForm) {
        if (!CheckUtil.isPhoneNo(loginForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(loginForm.getPhone());
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }
        if (!userEntity.getPassword().equals(loginForm.getPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_LOGIN_PASSWORD_ERROR);
        }

        return userInfoResponse(userEntity);
    }

    @Override
    public GenericResponse modify(UserInfoModifyForm modifyForm) {
        if (!CheckUtil.isPhoneNo(modifyForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(modifyForm.getPhone());
        if (userEntity != null && !userEntity.getId().equals(modifyForm.getId())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PHONE_HAS_REGISTERED);
        }

        userEntity = userMapper.selectById(modifyForm.getId());
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }

        BeanUtils.copyProperties(modifyForm, userEntity);
        userMapper.update(userEntity);

        return userInfoResponse(userEntity);
    }

    @Override
    public GenericResponse passwordModify(UserPasswordModifyForm modifyForm) {
        if (!modifyForm.getNewPassword().equals(modifyForm.getEnterPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PASSWORD_ENTER_ERROR);
        }
        UserEntity userEntity = userMapper.selectById(modifyForm.getId());
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }
        if (!userEntity.getPassword().equals(modifyForm.getOldPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PASSWORD_ERROR);
        }

        userEntity.setPassword(modifyForm.getEnterPassword());
        userMapper.update(userEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse userInfo(UserInfoDTO userInfoDTO) {
        UserEntity userEntity = null;
        if (userInfoDTO.getId() != null) {
            userEntity = userMapper.selectById(userInfoDTO.getId());
        } else if (userInfoDTO.getPhone() != null) {
            userEntity = userMapper.selectByPhoneNo(userInfoDTO.getPhone());
        } else if (userInfoDTO.getToken() != null) {
            userEntity = userMapper.selectById(UserUtils.getUserId(userInfoDTO.getToken()));
        }

        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }

        return userInfoResponse(userEntity);
    }

    @Override
    public GenericResponse userAll(UserSearch userSearch) {
        List<UserInfoVO> userInfoVOS = new ArrayList<>();
        List<UserEntity> userEntities = userMapper.selectByPage(userSearch);
        Integer total = userMapper.selectByPageTotal(userSearch);
        if (userEntities != null) {
            userEntities.forEach(userEntity -> userInfoVOS.add(generateUserInfo(userEntity)));
        }
        PageResponse<UserInfoVO> pageResponse = new PageResponse<>();
        pageResponse.setSize(userInfoVOS.size());
        pageResponse.setList(userInfoVOS);
        pageResponse.setTotal(total);

        return new GenericResponse<>(pageResponse);
    }

    /**
     * 订单状态：0未付款，1已付款，2待发货，3已发货，4已签收，5退货中，6已退货，7完成交易，8已完成评价
     */
    @Override
    public GenericResponse creditGet(Long userId) {
        // 只统计完成交易和完成评价的积分记录 (7完成交易，8已完成评价)
//        List<Integer> orderStatusList = Arrays.asList(7, 8);
        List<Integer> orderStatusList = Arrays.asList(8);
        List<CreditGetByOrderVO> list = orderMapper.selectCreditByUser(userId, orderStatusList);

        return new GenericResponse<>(list);
    }

    @Override
    public GenericResponse creditConsume(Long userId) {
        List<CreditConsumeBySendGiftVO> list = sentgiftMapper.sendGiftByUser(userId);
        Long totalCredit = sentgiftMapper.sendGiftByUserTotal(userId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("total", totalCredit);

        return new GenericResponse<>(jsonObject);
    }

    @Override
    public GenericResponse fixCredit(Long userId) {
        Long creditTotal = orderMapper.selectCreditByUserTotal(userId, Arrays.asList(7, 8));
        if (creditTotal == null) {
            creditTotal = 0L;
        }
        Long consumeTotal = sentgiftMapper.sendGiftByUserTotal(userId);
        if (consumeTotal == null) {
            consumeTotal = 0L;
        }

        // 正确的积分
        Long realCreditTotal = creditTotal - consumeTotal;

        // 实际的积分
        UserEntity userEntity = userMapper.selectById(userId);
        if (realCreditTotal.equals(userEntity.getCredit())) {
            return GenericResponse.SUCCESS;
        }

        // 积分不正确,更新积分
        userEntity.setCredit(realCreditTotal);
        userMapper.update(userEntity);

        return GenericResponse.SUCCESS;
    }
}
