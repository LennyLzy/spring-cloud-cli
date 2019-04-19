package com.jeenny.springcloud.response;

/**
 *
 * http请求返回的最外层对象
 * Created by sdbus on 2018-7-25.
 */
public class Result<T> {

    //错误码
    private Integer code;

    //提示信息
    private String msg;

    //具体的内容
    private T content;

    public Result() {
    }

    public Integer getCode() {

        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T data) {
        this.content = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", body=" + content +
                '}';
    }
}
