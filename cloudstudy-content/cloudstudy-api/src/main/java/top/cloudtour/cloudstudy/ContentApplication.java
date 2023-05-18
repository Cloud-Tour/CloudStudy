package top.cloudtour.cloudstudy;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author cloudtour
 * @version 1.0
 * @description 启动类
 * @date 2023/3/16 20:15
 */
@EnableSwagger2Doc
@SpringBootApplication
@EnableFeignClients(basePackages={"top.cloudtour.cloudstudy.feignclient"})
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
