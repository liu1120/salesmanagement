package com.zzlbe.core.request;

import lombok.Data;

/**
 * PROJECT: Sales
 * DESCRIPTION: 订单地址接口
 *
 * @author amos
 * @date 2019/5/1
 */
@Data
public class OrderAddressForm {

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
     * 用户手机号
     */
    private Long phone;
    /**
     * 用户姓名
     */
    private String name;

}
