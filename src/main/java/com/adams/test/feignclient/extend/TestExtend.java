package com.adams.test.feignclient.extend;

import com.adams.test.feignclient.handler.OperationClientHandler;
import org.springframework.stereotype.Component;

/**
 * @author Adams
 * @create 2019/3/5 15:50
 */
@Component
public class TestExtend extends OperationClientHandler {
    public void testExtend() {
        System.out.println("test");
    }
}
