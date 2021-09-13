package com.czg.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonObjectTest {
    public static void main(String[] args) {
        ExamEvaluateTaskSum examEvaluateTaskSum = new ExamEvaluateTaskSum();
        List<Integer> list = new ArrayList<>();
        list.add(13);
        list.add(14);
        examEvaluateTaskSum.setArtSubjectTypes(list);
        List<Integer> list1 = new ArrayList<>();
        list1.add(7);
        list1.add(14);
        list1.add(15);
        examEvaluateTaskSum.setMusicSubjectTypes(list1);
        examEvaluateTaskSum.setEvaluateType(1);
        examEvaluateTaskSum.setScoreMode(1);
        examEvaluateTaskSum.setEvaluatorNumber(3);
        examEvaluateTaskSum.setExamId(1456L);
        String  a = JSONObject.toJSONString(examEvaluateTaskSum);
        String bb = "[7,14,15]";
        List<Integer> cc = JSONArray.parseArray(bb,Integer.class);
        System.out.println(JSON.toJSONString(cc));

    }
}
