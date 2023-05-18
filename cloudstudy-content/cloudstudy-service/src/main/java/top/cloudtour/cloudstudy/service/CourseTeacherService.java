package top.cloudtour.cloudstudy.service;


import top.cloudtour.cloudstudy.model.dto.TeachplanDto;
import top.cloudtour.cloudstudy.model.po.CourseTeacher;

import java.util.List;

/**
 * @description 课教师管理业务接口
 * @author cloudtour
 * @date 2023/3/21 20:44
 * @version 1.0
 */
public interface CourseTeacherService {

    /**
     * @description 获取课程教师
     * @param id  课程id
     * @return void
     * @author cloudtour
     * @date 2023/3/21 20:47
     */
    List<CourseTeacher> getTeachers(Long id);

    /**
     * @description 在课程中添加教师
     * @param teacher  教师信息
     * @return void
     * @author cloudtour
     * @date 2023/3/21 21:04
     */
    CourseTeacher addTeacher(CourseTeacher teacher);

    /**
     * @description 删除课程中的教师
     * @param courseId  课程id
     * @param id  教师id
     * @return void
     * @author cloudtour
     * @date 2023/3/21 21:40
     */
    void deleteTeacher(Long courseId, Long id);
}
