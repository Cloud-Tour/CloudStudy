package top.cloudtour.cloudstudy.ucenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author cloudtour
 * @version 1.0
 * @description 搜索服务远程接口
 * @date 2023/3/29 17:52
 */
@FeignClient(value = "checkcode")
public interface CheckCodeClient {

    @PostMapping(value = "/checkcode/verify")
    public Boolean verify(@RequestParam("key") String key, @RequestParam("code")String code);
}

