package com.zzlbe.core.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * PROJECT: Sales
 * DESCRIPTION: 添加收货地址/修改收货地址
 *
 * @author amos
 * @date 2019/5/12
 */
@Data
public class UserAddressForm {

    /**
     * 地址ID
     */
    private Long id;
    /**
     * 省市县区地址ID
     */
    @NotNull(message = "省/直辖市不能为空")
    private Long provincecode;
    @NotNull(message = "县/区不能为空")
    private Long countycode;
    @NotNull(message = "乡镇/街道不能为空")
    private Long towncode;
    @NotNull(message = "城市不能为空")
    private Long citycode;
    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    private String info;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 收货人名字
     */
    @NotBlank(message = "收货人姓名不能为空")
    private String name;
    /**
     * 收货人手机号
     */
    @NotNull(message = "收货人手机号不能为空")
    private Long phone;
    /**
     * 地址状态，0默认有效，1无效
     */
    private Integer status;

}
