package top.cloudtour.cloudstudy.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import top.cloudtour.cloudstudy.ucenter.mapper.XcUserMapper;
import top.cloudtour.cloudstudy.ucenter.mapper.XcUserRoleMapper;
import top.cloudtour.cloudstudy.ucenter.model.dto.AuthParamsDto;
import top.cloudtour.cloudstudy.ucenter.model.dto.XcUserExt;
import top.cloudtour.cloudstudy.ucenter.model.po.XcUser;
import top.cloudtour.cloudstudy.ucenter.model.po.XcUserRole;
import top.cloudtour.cloudstudy.ucenter.service.AuthService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @author cloudtour
 * @version 1.0
 * @description 微信扫码认证
 * @date 2023/3/29 21:03
 */
@Slf4j
@Service("wx_authservice")
public class WxAuthServiceImpl implements AuthService {

    @Autowired
    XcUserMapper xcUserMapper;

    @Value("${weixin.appid}")
    String appid;
    @Value("${weixin.secret}")
    String secret;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    XcUserRoleMapper xcUserRoleMapper;
    @Autowired
    WxAuthServiceImpl currentProxy;

    /**
     * 申请访问令牌,响应示例
     {
     "access_token":"ACCESS_TOKEN",
     "expires_in":7200,
     "refresh_token":"REFRESH_TOKEN",
     "openid":"OPENID",
     "scope":"SCOPE",
     "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     }
     */
    private Map<String,String> getAccess_token(String code) {

        String wxUrl_template = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        //请求微信地址
        String wxUrl = String.format(wxUrl_template, appid, secret, code);

        log.info("调用微信接口申请access_token, url:{}", wxUrl);

        ResponseEntity<String> exchange = restTemplate.exchange(wxUrl, HttpMethod.POST, null, String.class);

        String result = exchange.getBody();
        log.info("调用微信接口申请access_token: 返回值:{}", result);
        Map<String,String> resultMap = JSON.parseObject(result, Map.class);

        return resultMap;
    }


    public XcUser wxAuth(String code){
        //收到code调用微信接口申请access_token
        Map<String, String> access_token_map = getAccess_token(code);
        if(access_token_map==null){
            return null;
        }
        System.out.println(access_token_map);
        String openid = access_token_map.get("openid");
        String access_token = access_token_map.get("access_token");
        //拿access_token查询用户信息
        Map<String, String> userinfo = getUserinfo(access_token, openid);
        if(userinfo==null){
            return null;
        }
        //添加用户到数据库
        //将用户信息保存到数据库
        XcUser xcUser = currentProxy.addWxUser(userinfo);
        return xcUser;

    }


    //微信扫码认证，不校验验证码，不校验密码
    @Override
    public XcUserExt execute(AuthParamsDto authParamsDto) {

        //账号
        String username = authParamsDto.getUsername();
        XcUser user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getUsername, username));
        if (user == null) {
            //返回空表示用户不存在
            throw new RuntimeException("账号不存在");
        }
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(user, xcUserExt);

        return xcUserExt;
    }

    /**获取用户信息，示例如下：
     {
     "openid":"OPENID",
     "nickname":"NICKNAME",
     "sex":1,
     "province":"PROVINCE",
     "city":"CITY",
     "country":"COUNTRY",
     "headimgurl": "https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     "privilege":[
     "PRIVILEGE1",
     "PRIVILEGE2"
     ],
     "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     }
     */
    private Map<String,String> getUserinfo(String access_token,String openid) {

        String wxUrl_template = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
        //请求微信地址
        String wxUrl = String.format(wxUrl_template, access_token,openid);

        log.info("调用微信接口申请access_token, url:{}", wxUrl);

        ResponseEntity<String> exchange = restTemplate.exchange(wxUrl, HttpMethod.POST, null, String.class);

        String result = exchange.getBody();
        log.info("调用微信接口申请access_token: 返回值:{}", result);
        Map<String,String> resultMap = JSON.parseObject(result, Map.class);

        return resultMap;
    }

    @Transactional
    public XcUser addWxUser(Map userInfo_map){
        String unionid = userInfo_map.get("unionid").toString();
        //根据unionid查询数据库
        XcUser xcUser = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getWxUnionid, unionid));
        if(xcUser!=null){
            return xcUser;
        }
        String userId = UUID.randomUUID().toString();
        xcUser = new XcUser();
        xcUser.setId(userId);
        xcUser.setWxUnionid(unionid);
        //记录从微信得到的昵称
        xcUser.setNickname(userInfo_map.get("nickname").toString());
        xcUser.setUserpic(userInfo_map.get("headimgurl").toString());
        xcUser.setName(userInfo_map.get("nickname").toString());
        xcUser.setUsername(unionid);
        xcUser.setPassword(unionid);
        xcUser.setUtype("101001");//学生类型
        xcUser.setStatus("1");//用户状态
        xcUser.setCreateTime(LocalDateTime.now());
        xcUserMapper.insert(xcUser);
        XcUserRole xcUserRole = new XcUserRole();
        xcUserRole.setId(UUID.randomUUID().toString());
        xcUserRole.setUserId(userId);
        xcUserRole.setRoleId("17");//学生角色
        xcUserRoleMapper.insert(xcUserRole);
        return xcUser;
    }


}

