package com.adams.test.feignclient.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Adams
 * @create 2019/2/20 15:45
 */
@RequestMapping("feign-client")
public interface IOperationApi {
    @GetMapping("/test")
    String testFeignClient();

    @GetMapping("/test/rest-template")
    String testTemplate();

    @GetMapping("/test/down-extend")
    String testDownExtend();
}
