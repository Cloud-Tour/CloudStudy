package top.cloudtour.cloudstudy.search.controller;


import top.cloudtour.cloudstudy.base.model.PageParams;
import top.cloudtour.cloudstudy.search.dto.SearchCourseParamDto;
import top.cloudtour.cloudstudy.search.dto.SearchPageResultDto;
import top.cloudtour.cloudstudy.search.po.CourseIndex;
import top.cloudtour.cloudstudy.search.service.CourseSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 课程搜索接口
 * @author cloudtour
 * @date 2023/3/27 21:44
 * @version 1.0
 */
@Api(value = "课程搜索接口",tags = "课程搜索接口")
 @RestController
 @RequestMapping("/course")
public class CourseSearchController {

 @Autowired
 CourseSearchService courseSearchService;


 @ApiOperation("课程搜索列表")
  @GetMapping("/list")
 public SearchPageResultDto<CourseIndex> list(PageParams pageParams, SearchCourseParamDto searchCourseParamDto){

    return courseSearchService.queryCoursePubIndex(pageParams,searchCourseParamDto);
   
  }
}
