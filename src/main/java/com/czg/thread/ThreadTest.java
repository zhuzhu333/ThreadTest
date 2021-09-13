package com.czg.thread;

public class ThreadTest {
    public static void main(String[] args) {
        Test test = new Test();
        Thread thread1 = new Thread(test);
        Thread thread2 = new Thread(test);
        Thread thread3 = new Thread(test);
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
