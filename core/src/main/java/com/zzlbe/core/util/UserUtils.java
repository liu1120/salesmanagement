package com.zzlbe.core.util;

public class UserUtils {

    /**
     * 根据用户id获取token
     *
     * @param id 用户id
     * @return token
     */
    public static String getUserToken(Long id) {
        return "TOKEN" + id;
    }

    /**
     * 根据token获取用户id
     *
     * @param token token
     * @return 用户id
     */
    public static Long getUserId(String token) {
        return Long.valueOf(token.replace("TOKEN", ""));
    }

}
