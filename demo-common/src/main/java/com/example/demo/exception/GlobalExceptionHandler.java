package com.example.demo.exception;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.demo.constant.es.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 17:21
 * @Version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BaseException.class)
    public AjaxResult<?> commonException(BaseException be) {
        Object requestId = RequestContextHolder.getRequestAttributes()
                .getAttribute("requestId", 0);
        log.error("异常：requestId[{}]，错误信息：{}", requestId, JSONUtils.toJSONString(be));
        return new AjaxResult<>(be.getErrorCode(), be.getErrorMessage(), null);
    }
    
}
