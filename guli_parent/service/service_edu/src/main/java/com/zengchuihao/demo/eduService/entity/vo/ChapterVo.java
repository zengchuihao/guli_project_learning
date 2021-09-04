package com.zengchuihao.demo.eduService.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    @ApiModelProperty(value = "章节id")
    private String id;

    @ApiModelProperty(value = "章节标题")
    private String title;

    @ApiModelProperty(value = "章节的所有小节集合")
    private List<VideoVo> children = new ArrayList<>();
}
