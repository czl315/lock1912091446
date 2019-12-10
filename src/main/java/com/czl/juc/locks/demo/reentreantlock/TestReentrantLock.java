package com.czl.juc.locks.demo.reentreantlock;

import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description: TODO()
 * @Date 2019/12/10
 * @Time 9:47
 */
@Slf4j
public class TestReentrantLock {
    //private static final ReentrantLock lock = new ReentrantLock(true);//重入锁,设定公平锁
    private final static ReentrantLock  lock = new ReentrantLock();//重入锁,默认非公平锁

    public static void main(String[] args) {
        //锁
        reentrantLockCase(lock,5);
    }

    /**
     *
     * @param lock
     * @param times
     */
    private static void reentrantLockCase(ReentrantLock lock,int times) {
        //锁测试
        for (int i = 0; i < times; i++) {
            new Thread(new ReentrantLockOne(lock)).start();
        }
    }

}


