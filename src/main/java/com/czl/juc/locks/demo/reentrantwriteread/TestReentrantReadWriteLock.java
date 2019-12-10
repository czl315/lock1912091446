package com.czl.juc.locks.demo.reentrantwriteread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description: TODO()
 * @Date 2019/12/6
 * @Time 14:17
 */
@Slf4j
public class TestReentrantReadWriteLock {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        //写锁测试
        //WriteLock(lock);

        //读锁测试
        ReadLock(lock);

        ReadAndWrite(lock);

        //尝试锁
        //writeLockTryLock(lock);

        //尝试锁（带有等待时间）
        //writeLockTryLockTimeOut(lock, 1, TimeUnit.SECONDS);

    }

    /**
     * 读写共存
     * @param lock
     */
    private static void ReadAndWrite(ReentrantReadWriteLock lock) {
        //锁测试
        for (int i = 0; i < 5; i++) {
            //new Thread(new WriteLockTryLockTime(lock, time, timeUnit)).start();
        }
    }

    /**
     * 尝试锁（带有等待时间）
     *
     * @param lock
     */
    private static void writeLockTryLockTimeOut(ReentrantReadWriteLock lock, int time, TimeUnit timeUnit) {
        //锁测试
        for (int i = 0; i < 5; i++) {
            new Thread(new WriteLockTryLockTime(lock, time, timeUnit)).start();
        }
    }

    /**
     * 写锁
     *
     * @param lock
     */
    static void WriteLock(ReentrantReadWriteLock lock) {
        //写锁测试
        for (int i = 0; i < 5; i++) {
            new Thread(new WriteLock(lock)).start();
        }
    }

    /**
     * 读锁
     *
     * @param lock
     */
    static void ReadLock(ReentrantReadWriteLock lock) {
        //读锁测试
        for (int i = 0; i < 5; i++) {
            new Thread(new ReadLock(lock)).start();
        }
    }

    /**
     * 尝试锁
     *
     * @param lock
     */
    static void writeLockTryLock(ReentrantReadWriteLock lock) {
        //锁测试
        for (int i = 0; i < 5; i++) {
            new Thread(new WriteLockTryLock(lock)).start();
        }
    }

}


