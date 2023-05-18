package top.cloudtour.cloudstudy.model.dto;

import lombok.Data;
import lombok.ToString;
import top.cloudtour.cloudstudy.model.po.Teachplan;
import top.cloudtour.cloudstudy.model.po.TeachplanMedia;

import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程计划树型结构dto
 * @date 2023/3/20 21:33
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {

    //课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;

    //子结点
    List<TeachplanDto> teachPlanTreeNodes;

}
