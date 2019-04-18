package com.zzlbe.advice;

import com.zzlbe.core.common.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * PROJECT: Sales
 * DESCRIPTION: note
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public GenericResponse handleThrowable(Throwable e) {
        LOGGER.error("{} >>> 报错位置: {}", e.toString(), e.getStackTrace()[0]);
        e.printStackTrace();
        return GenericResponse.FAIL;
    }

}
