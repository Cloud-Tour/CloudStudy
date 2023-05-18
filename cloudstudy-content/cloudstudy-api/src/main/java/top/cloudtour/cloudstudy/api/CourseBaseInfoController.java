package top.cloudtour.cloudstudy.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.cloudtour.cloudstudy.base.execption.ValidationGroups;
import top.cloudtour.cloudstudy.base.model.PageParams;
import top.cloudtour.cloudstudy.base.model.PageResult;
import top.cloudtour.cloudstudy.model.dto.AddCourseDto;
import top.cloudtour.cloudstudy.model.dto.CourseBaseInfoDto;
import top.cloudtour.cloudstudy.model.dto.EditCourseDto;
import top.cloudtour.cloudstudy.model.dto.QueryCourseParamsDto;
import top.cloudtour.cloudstudy.model.po.CourseBase;
import top.cloudtour.cloudstudy.service.CourseBaseInfoService;
import top.cloudtour.cloudstudy.util.SecurityUtil;


/**
 * @author cloudtour
 * @version 1.0
 * @description 课程信息编辑接口
 * @date 2023/3/16 20:11
 */
@Api(value = "课程信息编辑接口",tags = "课程信息编辑接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PreAuthorize("hasAuthority('xc_teachmanager_course_list')") //拥有权限才可访问
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams){
        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParams);
    }

    @ApiOperation("新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated({ValidationGroups.Inster.class})
                                                          AddCourseDto addCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);
    }

    @ApiOperation("根据课程id查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto editCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,editCourseDto);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/course/{courseId}")
    public void deleteCourseBase(@PathVariable Long courseId){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        courseBaseInfoService.deleteCourseBase(companyId,courseId);
    }

}
