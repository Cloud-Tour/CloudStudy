package top.cloudtour.cloudstudy.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.cloudtour.cloudstudy.media.model.po.MediaProcess;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cloudtour
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {

    /**
     * @description 根据分片参数获取待处理任务
     * @param shardTotal  分片总数
     * @param shardindex  分片序号
     * @param count 任务数
     * @return java.util.List<top.cloudtour.cloudstudy.media.model.po.MediaProcess>
     * @author cloudtour
     * @date 2023/3/24 21:08
     */
    @Select("SELECT t.* FROM media_process t WHERE t.id % #{shardTotal} = #{shardindex} and t.status='1' limit #{count}")
    List<MediaProcess> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardindex") int shardindex, @Param("count") int count);


    //开启任务-防止任务重复消费
    @Update("update media_process m set m.status='4' where m.status='1' and m.id=#{id}")
    int startTask(@Param("id") Long taskId);
}
