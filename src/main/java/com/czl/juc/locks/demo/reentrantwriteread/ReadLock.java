package com.czl.juc.locks.demo.reentrantwriteread;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description:
 * @Date 2019/12/9
 * @Time 15:08
 */
@Slf4j
public class ReadLock implements Runnable {

    ReentrantReadWriteLock lock ;

    public ReadLock(ReentrantReadWriteLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.readLock().lock();
            log.info(Thread.currentThread().getName() + "-lock.writeLock().lock()");
            log.info("【读锁】是【共享可重入的】。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
            log.info(Thread.currentThread().getName() + "-lock.writeLock().unlock()");
        }
    }
}


