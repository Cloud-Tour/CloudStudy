package top.cloudtour.cloudstudy.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程查询参数dto
 * @date 2023/3/16 20:01
 */
@Data
@ToString
public class QueryCourseParamsDto {

    //审核状态
    @ApiModelProperty("审核状态")
    private String auditStatus;
    //课程名称
    @ApiModelProperty("课程名称")
    private String courseName;
    //发布状态
    @ApiModelProperty("发布状态")
    private String publishStatus;

}
