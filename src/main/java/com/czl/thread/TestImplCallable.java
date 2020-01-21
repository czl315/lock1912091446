package com.czl.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author one3c-chenzhilong9
 * @Description:
 * @Date 2019/12/10
 * @Time 15:36
 */
@Slf4j
public class TestImplCallable {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Object obj = new CzlCallable();
            log.info(obj.toString());
        }
    }
}


