package com.adams.test.feignclient.factory;

import com.adams.test.feignclient.client.OperationClient;
import com.adams.test.feignclient.inter.OperationClientWithFactory;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Adams
 * @create 2019/2/20 15:38
 */
@Component
@Slf4j
public class OperationClientFallbackFactory implements FallbackFactory<OperationClient> {
    @Override
    public OperationClient create(Throwable throwable) {
        System.out.println(throwable);
        return new OperationClientWithFactory() {
            @Override
            public String getOperationalCentreById(int centerId) {
                return "test";
            }
        };
    }
}
