package top.cloudtour.cloudstudy.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import top.cloudtour.cloudstudy.config.MultipartSupportConfig;

/**
 * @author cloudtour
 * @version 1.0
 * @description 媒资管理服务远程接口
 * @date 2023/3/27 20:07
 */
@FeignClient(value = "media-api",configuration = MultipartSupportConfig.class,fallbackFactory = MediaServiceClientFallbackFactory.class)
@RequestMapping("/media")
public interface MediaServiceClient {

    @RequestMapping(value = "/upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("filedata") MultipartFile upload,
                      @RequestParam(value = "folder",required=false) String folder,
                      @RequestParam(value = "objectName",required=false) String objectName);
}

