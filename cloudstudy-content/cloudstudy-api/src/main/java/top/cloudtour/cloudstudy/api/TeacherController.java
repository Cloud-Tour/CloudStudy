package top.cloudtour.cloudstudy.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cloudtour.cloudstudy.model.po.CourseTeacher;
import top.cloudtour.cloudstudy.service.CourseTeacherService;

import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 教师编辑接口
 * @date 2023/3/21 20:38
 */
@Api(value = "教师编辑接口",tags = "教师编辑接口")
@RestController
public class TeacherController {

    @Autowired
    CourseTeacherService courseTeacherService;

    @ApiOperation("查询课程中的教师")
    @ApiImplicitParam(value = "id",name = "课程基础Id值",required = true,dataType = "Long",paramType = "path")
    @GetMapping("courseTeacher/list/{id}")
    public List<CourseTeacher> getTeachers(@PathVariable Long id){
        return courseTeacherService.getTeachers(id);
    }

    @ApiOperation("在课程中添加教师")
    @PostMapping("courseTeacher")
    public CourseTeacher addTeacher(@RequestBody CourseTeacher teacher){
        return courseTeacherService.addTeacher(teacher);
    }

    @ApiOperation("删除课程中的教师")
    @DeleteMapping("courseTeacher/course/{courseId}/{id}")
    public void deleteTeacher(@PathVariable Long courseId,
                              @PathVariable Long id){
        courseTeacherService.deleteTeacher(courseId,id);
    }
}
