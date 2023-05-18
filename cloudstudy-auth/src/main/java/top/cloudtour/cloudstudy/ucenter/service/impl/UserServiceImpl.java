package top.cloudtour.cloudstudy.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.cloudtour.cloudstudy.ucenter.mapper.XcMenuMapper;
import top.cloudtour.cloudstudy.ucenter.mapper.XcUserMapper;
import top.cloudtour.cloudstudy.ucenter.model.dto.AuthParamsDto;
import top.cloudtour.cloudstudy.ucenter.model.dto.XcUserExt;
import top.cloudtour.cloudstudy.ucenter.model.po.XcMenu;
import top.cloudtour.cloudstudy.ucenter.service.AuthService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 自定义UserDetailsService用来对接Spring Security
 * @date 2023/3/29 11:18
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    XcUserMapper xcUserMapper;

    @Autowired
    XcMenuMapper menuMapper;

    @Autowired
    ApplicationContext applicationContext;



    /**
     * @description 根据账号查询用户信息
     * @param s  账号
     * @return org.springframework.security.core.userdetails.UserDetails
     * @author cloudtour
     * @date 2023/3/29 11:19
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        AuthParamsDto authParamsDto = null;
        try {
            //将认证参数转为AuthParamsDto类型
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        } catch (Exception e) {
            log.info("认证请求不符合项目要求:{}",s);
            throw new RuntimeException("认证请求数据格式不对");
        }
        //认证方法
        String authType = authParamsDto.getAuthType();
        AuthService authService =  applicationContext
                .getBean(authType + "_authservice",AuthService.class);
        XcUserExt user = authService.execute(authParamsDto);

        return getUserPrincipal(user);
    }

    /**
     * @description 查询用户信息
     * @param user  用户id，主键
     * @return top.cloudtour.cloudstudy.ucenter.model.po.XcUser 用户信息
     * @author cloudtour
     * @date 2023/3/29 13:52
     */
    public UserDetails getUserPrincipal(XcUserExt user){
        String password = user.getPassword();
        //查询用户权限
        List<XcMenu> xcMenus = menuMapper.selectPermissionByUserId(user.getId());
        List<String> permissions = new ArrayList<>();
        if(xcMenus.size()<=0){
            //用户权限,如果不加则报Cannot pass a null GrantedAuthority collection
            permissions.add("p1");
        }else{
            xcMenus.forEach(menu->{
                permissions.add(menu.getCode());
            });
        }
        //将用户权限放在XcUserExt中
        user.setPermissions(permissions);

        //为了安全在令牌中不放密码
        user.setPassword(null);
        //将user对象转json
        String userString = JSON.toJSONString(user);
        String[] authorities = permissions.toArray(new String[0]);
        UserDetails userDetails = User.withUsername(userString).password(password).authorities(authorities).build();
        return userDetails;

    }


}
