package com.czl.thread;

/**
 * czl
 * createTime:2019/10/11
 */
public class NoSync implements Runnable {
    private static int count=0;

    @Override
    public void run() {
        add();
    }

    public static void add(){
//    public synchronized static void add(){
//        synchronized (NoSync.class){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
//        }
    }

    public static void main(String[] args) throws Exception {
        for(int i=0;i<5000;i++){
            new Thread(()-> NoSync.add()).start();
        }

        for (int i=1;i<=10;i++){
            Thread.sleep(1000);
            System.out.println(count);
        }
    }
}
