package com.zkd.cloudStudy.vo.vod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("课程观看进度")
@Data
public class CourseProgressVo {

    @ApiModelProperty(value = "课程ID")
    private Long courseId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "总时长")
    private Float durationSum;

    @ApiModelProperty(value = "观看进度总时长")
    private Float progressSum;

    @ApiModelProperty(value = "观看进度")
    private Integer progress;

}
