package com.jeenny.springcloud.response;



/**
 * Created by sdbus on 2018-7-25.
 */
public class ResultUtil {

    /**
     * 返回带data的成功结果
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setBody(object);
        return result;
    }

    /**
     * 返回不带data的成功结果
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 返回错误结果
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    /**
     * 返回错误结果
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
