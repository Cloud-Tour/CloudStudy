package top.cloudtour.cloudstudy.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.cloudtour.cloudstudy.ucenter.model.po.XcUser;
import top.cloudtour.cloudstudy.ucenter.service.impl.WxAuthServiceImpl;

import java.io.IOException;

/**
 * @author cloudtour
 * @version 1.0
 * @description TODO
 * @date 2023/3/29 21:02
 */
@Slf4j
@Controller
public class WxLoginController {
    @Autowired
    WxAuthServiceImpl wxAuthService;

    @RequestMapping("/wxLogin")
    public String wxLogin(String code, String state) throws IOException {
        log.debug("微信扫码回调,code:{},state:{}", code, state);
        XcUser xcUser = wxAuthService.wxAuth(code);
        if (xcUser == null) {
            return "redirect:http://www.51xuecheng.com/error.html";
        }
        String username = xcUser.getUsername();
        return "redirect:http://www.51xuecheng.com/sign.html?username=" + username + "&authType=wx";
    }

}
