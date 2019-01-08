package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Setter
@Getter
public class BaseResponse<T> implements Serializable {
    /*
    * 返回的数据
    * */
    private T data;
    /*
     * 是否成功
     * */
    private Boolean status;
    /*
     * 返回信息
     * */
    private String msg;
    /*
     * 返回的状态码
     * */
    private int code;
    /**
     * 服务器响应时间
     */
    private long timestamp;
    public BaseResponse() {
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public BaseResponse(boolean status) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.status = status;
    }

    public BaseResponse(boolean status, T data) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.status = status;
        this.data = data;
    }

    public BaseResponse(boolean status, T data, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.status = status;
        this.data = data;
        this.code = code;
    }
    public BaseResponse(boolean status,String msg ,int code, T data) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.status = status;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(boolean status, String msg) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.status = status;
        this.msg = msg;
    }

    public BaseResponse(boolean status, String msg, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.status = status;
        this.msg = msg;
        this.code = code;
    }



    public static BaseResponse ok() {
        return new BaseResponse(true,"success",100000);
    }

    public static <T> BaseResponse ok(T data) {
        return new BaseResponse(true,"success",100000,data);
    }

    public static <T> BaseResponse ok(int code) {
        return new BaseResponse(true, null, code);
    }

    public static <T> BaseResponse ok(T data, int code) {
        return new BaseResponse(true, data, code);
    }

    public static BaseResponse fail() {
        return new BaseResponse(false);
    }

    public static BaseResponse fail(String msg) {
        return new BaseResponse(false, msg);
    }

    public static BaseResponse fail(int code) {
        return new BaseResponse(false, null, code);
    }

    public static BaseResponse fail(int code, String msg) {
        return new BaseResponse(false, msg, code);
    }
}
