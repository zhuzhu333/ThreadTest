package com.czg.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class SubjectSetting {

    private Integer subjectCategory;
    private String subjectType;
    private List<JSONObject> subSubjectSetting;
}
