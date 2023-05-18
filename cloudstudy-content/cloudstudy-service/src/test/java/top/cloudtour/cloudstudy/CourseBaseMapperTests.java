package top.cloudtour.cloudstudy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cloudtour.cloudstudy.mapper.CourseBaseMapper;
import top.cloudtour.cloudstudy.model.dto.CourseCategoryTreeDto;
import top.cloudtour.cloudstudy.model.po.CourseBase;
import top.cloudtour.cloudstudy.service.CourseCategoryService;

import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description TODO
 * @date 2023/3/16 20:47
 */
@SpringBootTest
class CourseBaseMapperTests {

    @Autowired
    CourseBaseMapper courseBaseMapper;


    @Autowired
    CourseCategoryService courseCategoryService;


    @Test
    void testqueryTreeNodes() {
        List<CourseCategoryTreeDto> categoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println(categoryTreeDtos);
    }

    @Test
    void testCourseBaseMapper() {
        CourseBase courseBase = courseBaseMapper.selectById(74L);
        Assertions.assertNotNull(courseBase);
    }

}
