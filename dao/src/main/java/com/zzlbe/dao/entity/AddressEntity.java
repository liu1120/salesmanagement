package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * DESCRIPTION: 地址表
 *
 * @author ls
 * @date 2019/4/21
 */
@Data
@Accessors(chain = true)
public class AddressEntity {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 区地址 json
     * { "province_code":"410000000000" , "county_code":"410100000000"  , "town_code":"410102000000"  , "village_code":"410102005000"}
     */
    private String address;

    /**
     * 具体地址
     */
    private String info;

    /**
     * 用户id
     */
    private int uid;

    /**
     * 用户姓名
     */
    private int uname;
    /**
     * 用户手机号
     */
    private int phone;

    /**
     * 地址状态，0默认有效，1无效
     */
    private int status;

}