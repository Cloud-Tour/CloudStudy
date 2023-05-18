package top.cloudtour.cloudstudy.ucenter.service;

import top.cloudtour.cloudstudy.ucenter.model.dto.AuthParamsDto;
import top.cloudtour.cloudstudy.ucenter.model.dto.XcUserExt;

/**
 * @description 认证service
 * @author cloudtour
 * @date 2023/3/29 13:40
 * @version 1.0
 */

public interface AuthService {
    /**
     * @description 认证方法
     * @param authParamsDto 认证参数
     * @return top.cloudtour.cloudstudy.ucenter.model.po.XcUser 用户信息
     * @author cloudtour
     * @date 2023/3/29 13:41
     */
    XcUserExt execute(AuthParamsDto authParamsDto);

}
