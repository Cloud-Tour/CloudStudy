package top.cloudtour.cloudstudy.auth.controller;

import top.cloudtour.cloudstudy.ucenter.mapper.XcUserMapper;
import top.cloudtour.cloudstudy.ucenter.model.po.XcUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cloudtour
 * @version 1.0
 * @description 测试controller
 * @date 2023/3/28 11:25
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    XcUserMapper userMapper;


    @RequestMapping("/login-success")
    public String loginSuccess() {

        return "登录成功";
    }


    @RequestMapping("/user/{id}")
    public XcUser getuser(@PathVariable("id") String id) {
        XcUser xcUser = userMapper.selectById(id);
        return xcUser;
    }

    @RequestMapping("/r/r1")
    public String r1() {
        return "访问r1资源";
    }

    @RequestMapping("/r/r2")
    public String r2() {
        return "访问r2资源";
    }



}
