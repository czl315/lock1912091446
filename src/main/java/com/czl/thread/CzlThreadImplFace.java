package com.czl.thread;

/**
 * czl
 * createTime:2019/10/8
 */
public class CzlThreadImplFace implements Runnable {
    @Override
    public void run() {
        System.out.println("[实现Runnable]czl线程名称："+Thread.currentThread().getName());
    }
}
