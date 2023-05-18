package top.cloudtour.cloudstudy.search.service;


import top.cloudtour.cloudstudy.base.model.PageParams;
import top.cloudtour.cloudstudy.search.dto.SearchCourseParamDto;
import top.cloudtour.cloudstudy.search.dto.SearchPageResultDto;
import top.cloudtour.cloudstudy.search.po.CourseIndex;

/**
 * @description 课程搜索service
 * @author cloudtour
 * @date 2023/3/27 19:33
 * @version 1.0
 */
public interface CourseSearchService {


    /**
     * @description 搜索课程列表
     * @param pageParams 分页参数
     * @param searchCourseParamDto 搜索条件
     * @return top.cloudtour.cloudstudy.base.model.PageResult<top.cloudtour.cloudstudy.search.po.CourseIndex> 课程列表
     * @author cloudtour
     * @date 2023/3/27 20:45
    */
    SearchPageResultDto<CourseIndex> queryCoursePubIndex(PageParams pageParams, SearchCourseParamDto searchCourseParamDto);

 }
