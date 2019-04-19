package com.jeenny.springcloud.controller.advice;



import com.jeenny.springcloud.response.ResultEnum;
import com.jeenny.springcloud.response.ResultUtil;
import com.jeenny.springcloud.response.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类
 * Created by sdbus on 2018-7-25.
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Object handleUsernameNotFoundException(HttpServletRequest request, HttpServletResponse response, Exception e) throws  Exception{
        return ResultUtil.error(200,e.getMessage());
    }


    @ExceptionHandler(value = Exception.class)
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws  Exception{
        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            return ResultUtil.error(customException.getCode(), customException.getMessage());
        } else {
            e.printStackTrace();
            return ResultUtil.error(-1,"系统异常");
        }
    }
//    算术异常类：ArithmeticExecption
//    空指针异常类：NullPointerException
//    类型强制转换异常：ClassCastException
//    数组负下标异常：NegativeArrayException
//    数组下标越界异常：ArrayIndexOutOfBoundsException
//    违背安全原则异常：SecturityException
//    文件已结束异常：EOFException
//    文件未找到异常：FileNotFoundException
//    字符串转换为数字异常：NumberFormatException
//    操作数据库异常：SQLException
//    输入输出异常：IOException
//    方法未找到异常：NoSuchMethodException

}
