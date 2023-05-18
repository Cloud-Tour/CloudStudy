package top.cloudtour.cloudstudy.service;

import top.cloudtour.cloudstudy.model.dto.BindTeachplanMediaDto;
import top.cloudtour.cloudstudy.model.dto.SaveTeachplanDto;
import top.cloudtour.cloudstudy.model.dto.TeachplanDto;
import top.cloudtour.cloudstudy.model.po.TeachplanMedia;

import java.util.List;

/**
 * @description 课程基本信息管理业务接口
 * @author cloudtour
 * @date 2023/3/20 22:18
 * @version 1.0
 */
public interface TeachplanService {

    /**
     * @description 查询课程计划树型结构
     * @param courseId  课程id
     * @return List<TeachplanDto>
     * @author cloudtour
     * @date 2023/3/20 22:18
     */
    List<TeachplanDto> findTeachplayTree(long courseId);

    /**
     * @description 保存课程计划
     * @param teachplanDto  课程计划信息
     * @return void
     * @author cloudtour
     * @date 2023/3/20 22:45
     */
    void saveTeachplan(SaveTeachplanDto teachplanDto);

    /**
     * @description 删除课程计划
     * @param id  章节编号id
     * @return void
     * @author cloudtour
     * @date 2023/3/21 19:17
     */
    void deleteTeachplan(Long id);

    /**
     * @description 向下移动课程计划
     * @param id  章节编号id
     * @return void
     * @author cloudtour
     * @date 2023/3/21 19:47
     */
    void movedownTeachplan(Long id);

    /**
     * @description 向上移动课程计划
     * @param id  章节编号id
     * @return void
     * @author cloudtour
     * @date 2023/3/21 20:27
     */
    void moveupTeachplan(Long id);

    /**
     * @description 教学计划绑定媒资
     * @param bindTeachplanMediaDto
     * @return top.cloudtour.cloudstudy.model.po.TeachplanMedia
     * @author cloudtour
     * @date 2023/3/25 15:52
     */
    TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    /**
     * @description 教学计划绑定媒资
     * @param teachPlanId 课程计划id
     * @param mediaId 视频文件id
     * @return void
     * @author cloudtour
     * @date 2023/3/25 16:05
     */
    void DeleteAssociationMedia(long teachPlanId, String mediaId);
}
