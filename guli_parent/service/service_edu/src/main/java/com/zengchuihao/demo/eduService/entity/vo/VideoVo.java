package com.zengchuihao.demo.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVo {

    @ApiModelProperty(value = "小节id")
    private String id;

    @ApiModelProperty(value = "小节标题")
    private String title;

    @ApiModelProperty(value = "视频id")
    private String videoSourceId;
}
