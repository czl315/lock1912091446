package com.czl.thread;

/**
 * @author one3c-chenzhilong9
 * @Description: TODO()
 * @Date 2019/12/10
 * @Time 15:36
 */
public class TestExtendsThread {

    public static void main(String[] args) {
        //CzlThread t1 = new CzlThread();
        //CzlThread t2 = new CzlThread();
        //t1.start();
        //t2.start();

        for (int i = 0; i < 5; i++) {
            new CzlThread().start();
        }

        //CzlThreadImplFace t3 = new CzlThreadImplFace();
        //CzlThreadImplFace t4 = new CzlThreadImplFace();
        //new Thread(t3).start();
        //new Thread(t4).start();
    }
}


