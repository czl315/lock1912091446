package com.czl.juc.locks.demo.reentreantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description: 重入锁
 * @Date 2019/12/9
 * @Time 15:08
 */
@Slf4j
public class ReentrantLockOne implements Runnable {
    ReentrantLock reentrantLock;

    public ReentrantLockOne(ReentrantLock lock) {
        this.reentrantLock = lock;
    }

    @Override
    public void run() {
        try {
            reentrantLock.lock();
            log.info("【重入锁{}】-【锁定】。。。", Thread.currentThread().getName());
            log.info("【重入锁{}】-【锁定后处理业务】【是否是公平锁：{}】", Thread.currentThread().getName(), reentrantLock.isFair());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
            log.info("【重入锁{}】-【解锁】。。。", Thread.currentThread().getName());
        }
    }
}


