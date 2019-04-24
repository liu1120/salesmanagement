package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * PROJECT: Sales
 * DESCRIPTION: 用户表
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@Data
@Accessors(chain = true)
public class UserEntity {

    /**
     * 用户编号
     */
    private Long id;
    /**
     * 用户名字
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 微信
     */
    private String wechat;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 真实名字
     */
    private String realname;
    /**
     * 地址
     */
    private String address;
    /**
     * 积分
     */
    private Long credit;

    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 会话密钥
     */
    private String sessionKey;

}
