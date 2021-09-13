package com.czg.test;

import java.util.ArrayList;
import java.util.List;

public class StringTest {
    public static void main(String[] args) {
        String aa = "紫砂壶最有名的产地是（  ）。<img>https://oss.yishupingce.com/test/jpg/2019/12/17/5df8bc1db5512967295823.jpg</img> adfqf";
//        String prefix = "https://oss.yishupingce.com";
//        String bb = aa.substring(prefix.length());
        List<String> cc=getChildMsgByParent(aa,"<img>","</img>");
        String dd = aa.substring(0,aa.indexOf("<img>"));
        System.out.println(cc.get(0));
    }


    /**
     * 把长报文拆分成多个子报文
     * 拆分规则：以begin开始，以end结尾
     * @param info 待拆分的长报文
     * @param begin 开始字符
     * @param end 结尾字符
     * @return 符合规则的子报文集合
     */
    public static List<String> getChildMsgByParent(String info, String begin, String end){
        //通过起始字符拆分成数组
        String[] split = info.split(begin);
        List<String> list = new ArrayList<>();
        //遍历，从第二个元素开始取值（第一个元素为无效元素）
        for (int i = 1; i < split.length; i++) {
            String str = split[i].substring(0,split[i].lastIndexOf(end)
            );
            if (str.length() > end.length()) {
                list.add(str);
            }
        }
        return list;
    }
}
