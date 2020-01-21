package com.czl.juc.locks.demo.reentrantwriteread;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**tryLock()-尝试锁
 * @author one3c-chenzhilong9
 * @Description:
 * @Date 2019/12/9
 * @Time 15:08
 */
@Slf4j
public class WriteLockTryLock implements Runnable {

    ReentrantReadWriteLock lock ;

    public WriteLockTryLock(ReentrantReadWriteLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
            boolean lockFlag = lock.writeLock().tryLock();
            if(lockFlag){
                try {
                    log.info(Thread.currentThread().getName() + "-lock.writeLock().tryLock()");
                    log.info(Thread.currentThread().getName() + "-尝试锁取到了{}"+lock.writeLock().tryLock());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                    log.info(Thread.currentThread().getName() + "-lock.writeLock().unlock()");
                }
            }else{
                log.info(Thread.currentThread().getName() + "-尝试锁不能获取{}"+lock.writeLock().tryLock());
            }

    }
}


