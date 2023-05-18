package top.cloudtour.cloudstudy.service;

import top.cloudtour.cloudstudy.base.model.PageParams;
import top.cloudtour.cloudstudy.base.model.PageResult;
import top.cloudtour.cloudstudy.model.dto.AddCourseDto;
import top.cloudtour.cloudstudy.model.dto.CourseBaseInfoDto;
import top.cloudtour.cloudstudy.model.dto.EditCourseDto;
import top.cloudtour.cloudstudy.model.dto.QueryCourseParamsDto;
import top.cloudtour.cloudstudy.model.po.CourseBase;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程基本信息管理业务接口
 * @date 2023/3/16 21:02
 */
public interface CourseBaseInfoService  {

    /**
     * @description 课程查询接口
     * @param pageParams 分页参数
     * @param queryCourseParamsDto 条件条件
     * @return top.cloudtour.cloudstudy.base.model.PageResult<top.cloudtour.cloudstudy.model.po.CourseBase>
     * @author cloudtour
     * @date 2023/3/16 21:03
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * @description 添加课程基本信息
     * @param companyId  教学机构id
     * @param addCourseDto  课程基本信息
     * @return top.cloudtour.cloudstudy.model.dto.CourseBaseInfoDto
     * @author cloudtour
     * @date 2023/3/17 22:51
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * @description 根据id查询课程基本信息
     * @param courseId  课程id
     * @return top.cloudtour.cloudstudy.model.dto.CourseBaseInfoDto
     * @author cloudtour
     * @date 2023/3/20 20:58
     */
    CourseBaseInfoDto getCourseBaseInfo(long courseId);

    /**
     * @description 修改课程信息
     * @param companyId  机构id
     * @param dto  课程信息
     * @return top.cloudtour.cloudstudy.model.dto.CourseBaseInfoDto
     * @author cloudtour
     * @date 2023/3/20 21:04
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);

    /**
     * @description 删除课程信息
     * @param companyId  机构id
     * @param courseId  课程id
     * @return void
     * @author cloudtour
     * @date 2023/3/21 21:54
     */
    void deleteCourseBase(Long companyId, Long courseId);
}
