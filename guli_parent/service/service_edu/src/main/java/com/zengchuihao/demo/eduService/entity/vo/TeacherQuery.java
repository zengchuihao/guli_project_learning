package com.zengchuihao.demo.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {

    @ApiModelProperty(value = "讲师名称")
    private String name;

    @ApiModelProperty(value = "讲师等级")
    private Integer level;

    @ApiModelProperty(value = "开始时间", example = "2021-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "结束时间")
    private String end;


}
