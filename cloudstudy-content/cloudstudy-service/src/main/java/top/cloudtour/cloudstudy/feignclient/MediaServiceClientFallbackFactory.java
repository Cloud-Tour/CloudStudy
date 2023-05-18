package top.cloudtour.cloudstudy.feignclient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cloudtour
 * @version 1.0
 * @description 远程服务出错降级策略
 * @date 2023/3/27 20:14
 */
@Slf4j
@Component
public class MediaServiceClientFallbackFactory implements FallbackFactory<MediaServiceClient> {
    @Override
    public MediaServiceClient create(Throwable throwable) {
        return new MediaServiceClient(){
            @Override
            public String uploadFile(MultipartFile upload, String folder, String objectName) {
                log.error("远程调用媒资管理服务熔断异常：{}",throwable.getMessage());
                return null;
            }
        };
    }
}
