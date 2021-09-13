package com.czg;

public class MyJni {
    static {
        //全路径名
        System.load("D:\\dll\\myjni.dll");
        //只要文件名，路径需要添加到环境变量中
//        System.out.println(System.getProperty("java.library.path"));
//        System.loadLibrary("myjni");
    }
    public native int  numberPlus(int i ,int j);

    public static void main(String[] args) {
        int i=new MyJni().numberPlus(2,4);
        System.out.println(i);
    }
}
