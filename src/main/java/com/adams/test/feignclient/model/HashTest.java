package com.adams.test.feignclient.model;

import lombok.Data;

/**
 * @author Adams
 * @create 2019/7/16 10:56
 */
@Data
public class HashTest {
    private String test;

    @Override
    public int hashCode() {
        return 1234567;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("test");
    }
}
