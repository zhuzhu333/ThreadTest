package com.czg.test;


import lombok.Data;

import java.util.List;

@Data
public class ExamEvaluateTaskSum {
    private Long examId;
    private List<Integer> musicSubjectTypes;
    private List<Integer> artSubjectTypes;    private Integer evaluatorNumber;
    private Integer scoreMode;
    private Integer evaluateType;
    private Integer courseType;

}
