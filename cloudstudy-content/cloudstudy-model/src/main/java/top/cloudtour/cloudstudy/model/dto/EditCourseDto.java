package top.cloudtour.cloudstudy.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cloudtour
 * @version 1.0
 * @description 添加课程dto
 * @date 2023/3/20 20:53
 */
@Data
@ApiModel(value="EditCourseDto", description="修改课程基本信息")
public class EditCourseDto extends AddCourseDto {

    @ApiModelProperty(value = "课程名称", required = true)
    private Long id;

}