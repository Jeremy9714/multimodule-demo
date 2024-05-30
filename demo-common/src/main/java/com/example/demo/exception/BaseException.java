package com.example.demo.exception;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 17:12
 * @Version: 1.0
 */
@Data
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1998063243843477017L;
    private static final Logger logger = LoggerFactory.getLogger(BaseException.class);
    private int errorCode;
    //    private String errorView;
    private String errorMessage;

    public BaseException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BaseException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        if (logger.isDebugEnabled()) {
            logger.debug("{code:" + errorCode + ",msg:" + errorMessage + "}");
        }
    }

    public BaseException(int errorCode, String errorMessage, Throwable t) {
        super(t);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

//    public BaseException(String errorView, int errorCode, String errorMessage) {
//        if (StringUtils.isNotEmpty(errorView)) {
//            throw new IllegalArgumentException("The parameter errorView can not be blank.");
//        } else {
//            this.errorCode = errorCode;
//            this.errorView = errorView;
//            this.errorMessage = errorMessage;
//            if (logger.isDebugEnabled()) {
//                logger.debug("{code:" + errorCode + ",view:" + errorView + ",msg:" + errorMessage + "}");
//            }
//        }
//    }
}
