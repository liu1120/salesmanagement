package com.zzlbe.core.dto.wx;

/**
 * PROJECT: sales management
 * DESCRIPTION: 微信Response错误码
 *
 * @author amos.wang
 * @date 2019/4/23
 */
public enum WxErrorCodeEnum {

    /**
     * 错误码
     */
    SYSTEM_BUSY(-1, "系统繁忙，此时请开发者稍候再试"),
    SUCCESS(0, "请求成功"),
    CODE_ERROR(40029, "CODE无效"),
    OVERRUN(45011, "频率限制，每个用户每分钟100次");

    private final Integer code;
    private final String msg;

    WxErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static WxErrorCodeEnum getValue(Integer code) {
        if (code == null) {
            return null;
        }
        for (WxErrorCodeEnum value : WxErrorCodeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

}
