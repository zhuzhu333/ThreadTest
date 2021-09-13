package com.czg.面试题;



/***************************************
 *
 * jdk源码&多线程&高并发-【阶段1、深入多线程】
 * 主讲：smart哥
 *
 ***************************************/
public class NotifyTest {

    private static final Object LOCK = new Object();

    public void startThreadA(){
        new Thread(() -> {
            synchronized (LOCK){
                System.out.println(Thread.currentThread().getName() + " : " +"get lock");
                startThreadB();
                System.out.println(Thread.currentThread().getName() + " : " +"start wait");

                try {
                    LOCK.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " : " +"get lock after wait");
                System.out.println(Thread.currentThread().getName() + " : " +"release lock");
            }
        }, "thread-A").start();
    }

    public void startThreadB(){
        new Thread(()->{
            synchronized (LOCK){
                System.out.println(Thread.currentThread().getName() + " : " +"get lock");
                startThreadC();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " : " +"start notify");
                LOCK.notify();
                System.out.println(Thread.currentThread().getName() + " : " +"release lock");

            }
        },"thread-B").start();
    }

    public void startThreadC(){
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : " +"c thread is start");
            synchronized (LOCK){
                System.out.println(Thread.currentThread().getName() + " : " +"get lock");
                System.out.println(Thread.currentThread().getName() + " : " +"release lock");
            }
        }, "thread-C").start();
    }

    public static void main(String[] args){
        new NotifyTest().startThreadA();
    }
}
