package com.zzlbe.core.common;

import java.io.Serializable;

/**
 * PROJECT: Sales
 * DESCRIPTION: note
 *
 * @author duGraceful
 * @date 2018/12/12
 */
public class GenericResponse<T> implements Serializable {

    private static final long serialVersionUID = 3051339409110000405L;

    public static GenericResponse SUCCESS = new GenericResponse("1000", "成功!");
    public static GenericResponse FAIL = new GenericResponse("1001", "失败!");
    public static GenericResponse ERROR = new GenericResponse("1002", "参数错误!");
    public static GenericResponse ILLEGAL = new GenericResponse("1003", "非法请求!");

    private String code;
    private String message;
    private T body;

    public GenericResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public GenericResponse(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMsg();
    }

    public GenericResponse(T body) {
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.body = body;
    }

    public boolean successful() {
        return SUCCESS.getCode().equalsIgnoreCase(this.getCode());
    }

    public String getCode() {
        return code;
    }

    public GenericResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GenericResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getBody() {
        return body;
    }

    public GenericResponse setBody(T body) {
        this.body = body;
        return this;
    }

}
