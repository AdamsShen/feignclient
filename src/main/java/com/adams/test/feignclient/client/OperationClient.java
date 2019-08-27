package com.adams.test.feignclient.client;

import com.adams.test.feignclient.config.FeignErrorDecoderConfig;
import com.adams.test.feignclient.factory.OperationClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xuyg
 * @create 2018-09-02 16:14
 **/
@FeignClient(name = "operationClient", url = "http://localhost:8080/ovc-purchase-api", fallbackFactory = OperationClientFallbackFactory.class, configuration = FeignErrorDecoderConfig.class)
public interface OperationClient {

    @GetMapping("/operationalcentre/get/id/{id}")
    String getOperationalCentreById(@PathVariable("id") int centerId);
}
