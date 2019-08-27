package com.adams.test.feignclient;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adams
 * @create 2019/2/21 14:53
 */
public class DateTest extends AbstractTest {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date());

        List<Integer> testInt = Arrays.asList(1,2,3,4,5,6);
        System.out.println(testInt.stream().filter(t -> t > 3).collect(Collectors.toList()).toString());
        System.out.println(testInt.toString());
    }
}
