package com.czg;

import com.czg.utils.CalendarTemplate;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class MyObject {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        hashMap.put(1,"dqd");
        hashMap.get(1);
        System.out.println(String.format("%2d",Integer.valueOf("1")));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        System.out.println(format.format(new Date()));
        System.out.println(CalendarTemplate.getCurrentWeek(new Date()));
    }
}
