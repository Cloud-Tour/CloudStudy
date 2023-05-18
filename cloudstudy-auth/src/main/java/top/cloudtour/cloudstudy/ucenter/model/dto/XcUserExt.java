package top.cloudtour.cloudstudy.ucenter.model.dto;

import top.cloudtour.cloudstudy.ucenter.model.po.XcUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 用户扩展信息
 * @author cloudtour
 * @date 2023/3/28 13:56
 * @version 1.0
 */
@Data
public class XcUserExt extends XcUser {
    //用户权限
    List<String> permissions = new ArrayList<>();
}
