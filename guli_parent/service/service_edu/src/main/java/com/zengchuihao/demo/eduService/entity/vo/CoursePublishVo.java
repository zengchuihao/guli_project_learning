package com.zengchuihao.demo.eduService.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String price; //只用于显示
    private String cover;
    private Integer lessonNum;
    private String teacherName;
    private String subjectLevelOne;
    private String subjectLevelTwo;

}
