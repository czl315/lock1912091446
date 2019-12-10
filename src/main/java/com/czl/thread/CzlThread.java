package com.czl.thread;

public class CzlThread extends Thread {
    @Override
    public void run() {
        System.out.println("[继承Thread]czl线程名称："+Thread.currentThread().getName());
    }
}
