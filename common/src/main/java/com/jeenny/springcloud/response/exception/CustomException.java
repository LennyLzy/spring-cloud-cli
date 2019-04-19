package com.jeenny.springcloud.response.exception;


import com.jeenny.springcloud.response.ResultEnum;

/**
 * 自定义异常
 * Created by sdbus on 2018-7-25.
 */
public class CustomException extends RuntimeException{
    private int code;

    /**
     * 使用ResultEnum建立异常
     * @param resultEnum
     */
    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }


    /**
     * 建立使用ResultEnum建立异常，且为异常信息添加自定义信息
     * @param resultEnum
     * @param message
     */
    public CustomException(ResultEnum resultEnum, String message) {
        super(resultEnum.getMsg() + "，" + message);
        this.code = resultEnum.getCode();
    }

    /**
     * 自定义异常信息
     * @param code
     * @param message
     */
    public CustomException(int code, String message) {
        super(message);
        this.code = code;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
