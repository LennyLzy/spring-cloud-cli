package com.jeenny.springcloud.response;




import com.jeenny.springcloud.response.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws  Exception{

        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            return ResultUtil.error(customException.getCode(), customException.getMessage());
        }
        else {
            logger.error("\n【系统异常】", e);
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOW_ERROE);
        }

    }
}
