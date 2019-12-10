package com.czl.thread;

import java.util.concurrent.Callable;

/**
 * czl
 * createTime:2019/10/8
 */
public class CzlCallable implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println("[实现Callable]czl线程名称："+Thread.currentThread().getName());
        return Thread.currentThread().getName();
    }
}
