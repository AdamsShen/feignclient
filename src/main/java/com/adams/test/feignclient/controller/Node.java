package com.adams.test.feignclient.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Adams
 * @create 2019/8/27 11:01
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Node {
    private Object value;
    private Node next;
}
