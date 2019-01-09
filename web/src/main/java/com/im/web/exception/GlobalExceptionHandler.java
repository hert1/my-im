/*
package com.im.web.exception;

import com.im.api.dto.article.BaseResponse;
import com.im.api.enumu.RestCode;
import com.im.api.exception.TipException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

*
 * Created by BlueT on 2017/3/4.


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse exception(Exception e) {
        return BaseResponse.fail(100003, "系统异常");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponse parameterException(Exception e) {
        return BaseResponse.fail(100001, "参数异常！");
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public BaseResponse parameterExceptionToGet(Exception e) {
        return BaseResponse.fail(100001, "参数异常！");
    }

    @ExceptionHandler(value = TipException.class)
    @ResponseBody
    public BaseResponse tipException(Exception e) {
        for (RestCode info : RestCode.values()) {
            if ((info.getAction().equals(e.getMessage()))) {
                return BaseResponse.fail(Integer.parseInt(e.getMessage()), info.toString());
            }
        }
        return BaseResponse.fail(e.getMessage());
    }
}
*/
