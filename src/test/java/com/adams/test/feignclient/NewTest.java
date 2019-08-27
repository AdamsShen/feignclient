package com.adams.test.feignclient;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Adams
 * @create 2019/2/28 9:26
 */
public class NewTest extends AbstractTest {
    public static void main(String[] args) {
        StringJoiner joiner = new StringJoiner("erw");
        joiner.add("34");
        joiner.add("er");
        System.out.println(joiner.toString());

        List testList = Arrays.asList("12", "34", "56");
        System.out.println(testList.stream().collect(Collectors.joining(",")));
    }
}
