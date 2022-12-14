package com.zkd.cloudStudy.vo.live;


import com.zkd.cloudStudy.model.live.LiveCourseConfig;
import com.zkd.cloudStudy.model.live.LiveCourseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "LiveCourseConfig")
public class LiveCourseConfigVo extends LiveCourseConfig {

    @ApiModelProperty(value = "ๅๅๅ่กจ")
    private List<LiveCourseGoods> liveCourseGoodsList;
}