package com.czl.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description: TODO()
 * @Date 2019/12/9
 * @Time 10:53
 */
@Slf4j
public class LockDemo {
    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        log.info("main(String[] args)-start");
    }

}


