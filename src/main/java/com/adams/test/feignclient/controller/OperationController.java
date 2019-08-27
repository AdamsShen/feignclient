package com.adams.test.feignclient.controller;

import com.adams.test.feignclient.api.IOperationApi;
import com.adams.test.feignclient.client.OperationClient;
import com.adams.test.feignclient.extend.TestExtend;
import com.adams.test.feignclient.extend.TestExtendV2;
import com.adams.test.feignclient.handler.OperationClientHandler;
import com.adams.test.feignclient.model.HashTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * @author Adams
 * @create 2019/2/20 15:45
 */
@RestController
public class OperationController implements IOperationApi {
    @LoadBalanced
    RestTemplate restTemplate;
    @Resource
    OperationClient client;
    @Autowired
    @Qualifier(value = "testExtend")
    OperationClientHandler handler;
    static int i;

    @Override
    public String testFeignClient() {
        return client.getOperationalCentreById(1);
    }

    @Override
    public String testTemplate() {
        return restTemplate.getForEntity("/test", String.class).getBody();
    }

    public String testUpExtend() {
        handler.getOperationalCentreById(1);
        return "test";
    }

    @Override
    public String testDownExtend() {
        OperationClientHandler handler = new TestExtend();
        TestExtendV2 extendV2 = (TestExtendV2) handler;

        return "test";
    }

    /*public static void main(String[] args) {
        HashMap<HashTest, Integer> map = new HashMap<>();
        for (int i = 0; i < 11 ; i++) {
            HashTest test = new HashTest();
            test.setTest(String.valueOf(i));
            map.put(test, i);
        }
        System.out.println(map.size());
    }*/

    /*public static void main(String[] args) throws InterruptedException {
        HashTest test = new HashTest();
        WeakReference<HashTest> weakReference = new WeakReference<HashTest>(test);
        test = null;
        System.out.println("123");
        Thread.currentThread().join();
    }*/
    public static void main(String[] args) {
//        int i;
        System.out.println(++i);
    }
}
