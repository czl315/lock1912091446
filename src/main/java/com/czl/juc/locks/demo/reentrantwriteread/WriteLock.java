package com.czl.juc.locks.demo.reentrantwriteread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description: TODO()
 * @Date 2019/12/9
 * @Time 15:08
 */
@Slf4j
public class WriteLock implements Runnable {

    ReentrantReadWriteLock lock ;

    public WriteLock(ReentrantReadWriteLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            //lock.writeLock().lockInterruptibly();
            lock.writeLock().lock();
            log.info(Thread.currentThread().getName() + "-lock.writeLock().lock()");
            log.info("写锁是独占的。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
            log.info(Thread.currentThread().getName() + "-lock.writeLock().unlock()");
        }
    }
}


