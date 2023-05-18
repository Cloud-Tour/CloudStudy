package top.cloudtour.cloudstudy.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.cloudtour.cloudstudy.feignclient.model.CourseIndex;

/**
 * @author cloudtour
 * @version 1.0
 * @description 搜索服务远程接口
 * @date 2023/3/27 22:36
 */
@FeignClient(value = "search")
public interface SearchServiceClient {

    @PostMapping("/search/index/course")
    public Boolean add(@RequestBody CourseIndex courseIndex);
}

