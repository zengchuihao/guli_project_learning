package com.zengchuihao.demo.eduService.entity.subject;

import lombok.Data;

import java.util.List;

@Data
public class FirstSubject {

    private String id;

    private String title;

    private List<SecondSubject> children;

}
