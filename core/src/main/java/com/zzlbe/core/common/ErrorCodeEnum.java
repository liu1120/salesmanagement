package com.zzlbe.core.common;

/**
 * PROJECT: Sales
 * DESCRIPTION: 错误状态码枚举（统一管理）
 * <br/>
 * 1XXX 开头错误码系统保留，不允许使用，建议每个业务指定一个起始错误码
 * <br/>
 *
 * @author amos
 * @date 2019/4/27
 */
public enum ErrorCodeEnum {

    /**
     * 用户相关
     */
    // USER("2000", "用户"),
    USER_NOT_FOUND("2001", "用户不存在!"),
    USER_PHONE_FORMAT_ERROR("2002", "手机号格式错误!"),
    USER_PHONE_HAS_REGISTERED("2003", "手机号已注册!"),
    USER_MODIFY_PHONE_HAS_REGISTERED("2004", "手机号已被占用!"),
    USER_LOGIN_PASSWORD_ERROR("2005", "密码错误!"),
    USER_MODIFY_PASSWORD_ERROR("2006", "原始密码错误!"),
    USER_MODIFY_PASSWORD_ENTER_ERROR("2007", "两次输入的不一致!"),


    /**
     * 销售员相关
     */
    // SELLER("3000", "销售员"),
    SELLER_NOT_FOUND("3001", "销售员不存在"),


    /**
     * 商品相关
     */
    // GOODS("4000", "商品"),
    GOODS_NOT_FOUND("4001", "商品不存在"),
    GOODS_REMOVED("4002", "商品已下架"),


    /**
     * 订单相关
     */
    // ORDER("5000", "订单"),
    ORDER_NOT_FOUND("5001", "订单不存在"),
    ORDER_MODIFY_COUNT_MIN_1("5002", "商品数量不能小于1"),
    ORDER_MODIFY_ERROR("5004", "如需修改，请申请售后"),
    ORDER_MODIFY_SELLER_ERROR("5005", "审核员身份有误"),
    ORDER_PAYMENT_ERROR("5006", "仅支持全额付款"),
    ORDER_CHECK_UNPAID("5007", "请提醒用户支付，支付完成可以审核"),
    ORDER_CREATE_SET_SELLER("5008", "下单请指定销售员"),
    ORDER_CREATE_SELLER_NOT_EXIT("5009", "该地区暂时不能下单"),
    ORDER_MODIFY_CHANGE_SELLER("5010", "暂不支持修改销售员"),
    ORDER_TRANSFER("5010", "运输中，请耐心等待"),
    ORDER_FINISH("5011", "订单状态不允许修改"),
    ORDER_PAYMENT_PAYED("5012", "订单已支付，请勿重复操作"),
    ORDER_AFTER_SALE_PROCESSING("5013", "售后处理中，请等待"),
    ORDER_AFTER_SALE_REFUND_PROCESSING("5014", "订单退款中，请等待"),
    ORDER_AFTER_SALE_NOT_FOUND("5015", "售后订单不存在"),
    ORDER_CHECKED("5016", "订单已审核完成"),
    ORDER_STATUS_ERROR("5017", "当前订单状态不支持本操作"),

    /**
     * 考勤相关
     */
    // ATTENDANCE("6000", "考勤"),
    ATTENDANCE_MODIFY_CLOSE("6001", "当前状态不允许修改"),

    /**
     * 投诉建议技术咨询相关
     */
    // SUGGEST("7000", "投诉建议技术咨询"),
    SUGGEST_REPLY_ERROR("7001", "回复无效, 主题不存在!"),
    SUGGEST_CLOSE_ERROR("7002", "关闭主题失败, 主题不存在!"),
    SUGGEST_CLOSE_ERROR_NOT_SPONSOR("7003", "关闭主题失败, 非发起人操作关闭!"),

    ;

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final String code;
    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
