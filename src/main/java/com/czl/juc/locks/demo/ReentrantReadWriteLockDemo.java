package com.czl.juc.locks.demo;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description: TODO()
 * @Date 2019/12/6
 * @Time 14:17
 */
@Slf4j
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock  = new ReentrantReadWriteLock();

        //写锁测试
        //WriteLock(lock);

        //读锁测试
        ReadLock(lock);

    }
    /**
     * 写锁
     * @param lock
     */
    static void WriteLock(ReentrantReadWriteLock lock){
        //写锁测试
        for (int i = 0; i < 5; i++) {
            new Thread(new WriteLock(lock)).start();
        }
    }
    /**
     * 读锁
     * @param lock
     */
    static void ReadLock(ReentrantReadWriteLock lock){
        //读锁测试
        for (int i = 0; i < 5; i++) {
            new Thread(new ReadLock(lock)).start();
        }
    }

}


