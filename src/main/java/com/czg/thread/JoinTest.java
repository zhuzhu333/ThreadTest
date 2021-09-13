package com.czg.thread;

public class JoinTest {
    static int THREAD_LENGTH = 100;

    public static void main(String[] args) {
        long startTime =System.currentTimeMillis();
        Thread[] threads = new Thread[THREAD_LENGTH];
        for (int i = 0;i<THREAD_LENGTH;i++){
            threads[i] = new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                calc();
            });
        }

        for (int i = 0;i<THREAD_LENGTH;i++){
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
/*        for (int i = 0;i<THREAD_LENGTH;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        System.out.println("cost time = "+(System.currentTimeMillis()-startTime));
    }
    static void calc(){
        int result = 0;
        for (int i =0;i<10000;i++){
            for (int j = 0;j<200;j++){
                result +=i;
            }
        }
    }

}
