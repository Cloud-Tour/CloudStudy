package top.cloudtour.cloudstudy.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.cloudtour.cloudstudy.ucenter.model.po.XcMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cloudtour
 */
public interface XcMenuMapper extends BaseMapper<XcMenu> {
    @Select("SELECT	* FROM xc_menu WHERE id IN (SELECT menu_id FROM xc_permission WHERE role_id IN ( SELECT role_id FROM xc_user_role WHERE user_id = #{userId} ))")
    List<XcMenu> selectPermissionByUserId(@Param("userId") String userId);
}
