package top.cloudtour.cloudstudy.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cloudtour.cloudstudy.model.dto.CourseCategoryTreeDto;
import top.cloudtour.cloudstudy.service.CourseCategoryService;

import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 数据字典前端控制器
 * @date 2023/3/17 20:15
 */
@Slf4j
@RestController
public class CourseCategoryController {

    @Autowired
    CourseCategoryService courseCategoryService;

    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }
}
