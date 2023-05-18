package top.cloudtour.cloudstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.cloudtour.cloudstudy.model.dto.TeachplanDto;
import top.cloudtour.cloudstudy.model.po.Teachplan;

import java.util.List;


/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author cloudtour
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {
    /**
     * @description 查询某课程的课程计划，组成树型结构
     * @param courseId
     * @return top.cloudtour.cloudstudy.model.dto.TeachplanDto
     * @author cloudtour
     * @date 2023/3/20 21:38
     */
    public List<TeachplanDto> selectTreeNodes(long courseId);
}
