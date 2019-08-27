package com.adams.test.feignclient.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * @author Adams
 * @create 2019/2/20 19:26
 */
public class FeignException extends HystrixBadRequestException {

    public FeignException(String message) {
        super(message);
    }

    public FeignException(String message, Throwable cause) {
        super(message, cause);
    }
}
