package com.czg.thread;

public class Test implements Runnable{

    @Override
    public void run() {
        int ticket = 100;
        Object object = new Object();
            synchronized (object){
                while (true){
                    if(ticket>0){
                        System.out.println(Thread.currentThread().getName()+":"+ticket);
                        ticket--;
                    }else break;
                }

        }

    }
}
