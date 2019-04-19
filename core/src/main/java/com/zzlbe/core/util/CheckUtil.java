package com.zzlbe.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PROJECT: Sales
 * DESCRIPTION: 校验类
 *
 * @author amos
 * @date 2019/4/16
 */
public class CheckUtil {

    private static final String ID_NO_CHECK = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
    private static final String PHONE_NO_CHECK = "^1([3-9])\\d{9}$";

    /**
     * 判断是不是手机号
     *
     * @param phoneNo 手机号
     * @return true: 是手机号; false: 不是
     */
    public static boolean isPhoneNo(String phoneNo) {
        Pattern pattern = Pattern.compile(PHONE_NO_CHECK);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    /**
     * 是不是身份证号
     *
     * @param idNo 身份证号
     * @return true: 是身份证号; false: 不是
     */
    public static boolean isIdentityNo(String idNo) {
        Pattern pattern = Pattern.compile(ID_NO_CHECK);
        Matcher matcher = pattern.matcher(idNo);
        return matcher.matches();
    }

}
