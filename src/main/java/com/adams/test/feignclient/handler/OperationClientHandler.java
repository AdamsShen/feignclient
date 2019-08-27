package com.adams.test.feignclient.handler;

import com.adams.test.feignclient.client.OperationClient;
import org.springframework.stereotype.Component;

/**
 * @author Adams
 * @create 2019/2/20 15:57
 */
//@Component
public class OperationClientHandler implements OperationClient {
    @Override
    public String getOperationalCentreById(int centerId) {
        return "test";
    }
}
