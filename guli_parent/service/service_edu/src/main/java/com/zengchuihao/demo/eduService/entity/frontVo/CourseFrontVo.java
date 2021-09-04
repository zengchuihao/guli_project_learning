package com.zengchuihao.demo.eduService.entity.frontVo;

import lombok.Data;

@Data
public class CourseFrontVo {

    //课程名称
    private String title;

    //讲师id
    private String teacherId;

    //一级分类id
    private String subjectParentId;

    //二级分类id
    private String subjectId;

    //销售量排序
    private String buyCountSort;

    //课程创建时间排序
    private String gmtCreateSort;

    //课程价格排序
    private String priceSort;

}
