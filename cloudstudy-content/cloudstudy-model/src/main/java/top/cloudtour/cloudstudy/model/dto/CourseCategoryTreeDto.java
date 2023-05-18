package top.cloudtour.cloudstudy.model.dto;

import lombok.Data;
import top.cloudtour.cloudstudy.model.po.CourseCategory;

import java.io.Serializable;
import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程分类树型结点dto
 * @date 2023/3/17 20:14
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    List childrenTreeNodes;
}