package com.czl.juc.locks.demo.reentrantwriteread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**tryLock()-尝试锁
 * @author one3c-chenzhilong9
 * @Description: TODO()
 * @Date 2019/12/9
 * @Time 15:08
 */
@Slf4j
public class WriteLockTryLockTime implements Runnable {

    ReentrantReadWriteLock lock ;
    int time;
    TimeUnit timeUnit;

    public WriteLockTryLockTime(ReentrantReadWriteLock lock,int time,TimeUnit timeUnit) {
        this.lock = lock;
        this.time = time;
        this.timeUnit = timeUnit;
    }

    @Override
    public void run() {
        boolean lockFlag = false;
        try {
            lockFlag = lock.writeLock().tryLock(time, timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(lockFlag){
                try {
                    log.info(Thread.currentThread().getName() + "-lock.writeLock().tryLock(timeout)");
                    log.info(Thread.currentThread().getName() + "-尝试锁取到了{}",lockFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                    log.info(Thread.currentThread().getName() + "-lock.writeLock().unlock(timeout)");
                }
            }else{
                log.info(Thread.currentThread().getName() + "-尝试锁不能获取{}",lockFlag);
            }

    }
}


