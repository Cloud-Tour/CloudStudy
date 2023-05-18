package top.cloudtour.cloudstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cloudtour.cloudstudy.base.execption.CloudStudyException;
import top.cloudtour.cloudstudy.mapper.TeachplanMapper;
import top.cloudtour.cloudstudy.mapper.TeachplanMediaMapper;
import top.cloudtour.cloudstudy.model.dto.BindTeachplanMediaDto;
import top.cloudtour.cloudstudy.model.dto.SaveTeachplanDto;
import top.cloudtour.cloudstudy.model.dto.TeachplanDto;
import top.cloudtour.cloudstudy.model.po.Teachplan;
import top.cloudtour.cloudstudy.model.po.TeachplanMedia;
import top.cloudtour.cloudstudy.service.TeachplanService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程计划service接口实现类
 * @date 2023/3/20 22:19
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public List<TeachplanDto> findTeachplayTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {

        //课程计划id
        Long id = teachplanDto.getId();
        //修改课程计划
        if(id!=null){
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }else{
            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count+1);
            BeanUtils.copyProperties(teachplanDto,teachplanNew);

            teachplanMapper.insert(teachplanNew);

        }

    }

    @Override
    public void deleteTeachplan(Long id) {
        LambdaQueryWrapper<Teachplan> wrapper = new LambdaQueryWrapper<>();
        //查询该章节下是否有子节
        wrapper.eq(Teachplan::getParentid,id);
        Integer count = teachplanMapper.selectCount(wrapper);
        if (count>0){
            throw new CloudStudyException("课程计划信息还有子级信息，无法操作");
        }

        teachplanMapper.deleteById(id);
        //该课程计划下绑定的媒资文件关系也删除
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
        .eq(TeachplanMedia::getTeachplanId,id));

    }

    @Override
    @Transactional
    public void movedownTeachplan(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        //若该计划已经是最下面的课程计划，则不做操作
        int count = getTeachplanCount(teachplan.getCourseId(), teachplan.getParentid());
        if (teachplan.getOrderby()!=count){
            //找出下级课程计划
            Teachplan nextT = getNextTeachplan(teachplan);
            //交换计划排序等级
            int tmp = nextT.getOrderby();
            nextT.setOrderby(teachplan.getOrderby());
            teachplan.setOrderby(tmp);
            teachplanMapper.updateById(teachplan);
            teachplanMapper.updateById(nextT);
        }

    }

    @Override
    @Transactional
    public void moveupTeachplan(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        //若该计划已经是最上面的课程计划，则不做操作
        if (teachplan.getOrderby()!=1){
            //找出上级课程计划
            Teachplan upT = getUpTeachplan(teachplan);
            //交换计划排序等级
            int tmp = upT.getOrderby();
            upT.setOrderby(teachplan.getOrderby());
            teachplan.setOrderby(tmp);
            teachplanMapper.updateById(teachplan);
            teachplanMapper.updateById(upT);
        }
    }

    @Transactional
    @Override
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        //教学计划id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            CloudStudyException.cast("教学计划不存在");
        }
        Integer grade = teachplan.getGrade();
        if(grade!=2){
            CloudStudyException.cast("只允许第二级教学计划绑定媒资文件");
        }
        //课程id
        Long courseId = teachplan.getCourseId();

        //先删除原来该教学计划绑定的媒资
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>().eq(TeachplanMedia::getTeachplanId,teachplanId));

        //再添加教学计划与媒资的绑定关系
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setMediaId(bindTeachplanMediaDto.getMediaId());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        teachplanMediaMapper.insert(teachplanMedia);
        return teachplanMedia;
    }

    @Transactional
    @Override
    public void DeleteAssociationMedia(long teachPlanId, String mediaId) {
        Teachplan teachplan = teachplanMapper.selectById(teachPlanId);
        if(teachplan==null){
            CloudStudyException.cast("教学计划不存在");
        }
        //课程id
        Long courseId = teachplan.getCourseId();
        LambdaQueryWrapper<TeachplanMedia> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeachplanMedia::getTeachplanId,teachPlanId);
        wrapper.eq(TeachplanMedia::getMediaId,mediaId);
        //删除绑定的媒资
        int delete = teachplanMediaMapper.delete(wrapper);
        if (delete<=0) CloudStudyException.cast("绑定视频删除失败");
    }


    /**
     * @description 获取最新的排序号
     * @param courseId  课程id
     * @param parentId  父课程计划id
     * @return int 最新排序号
     * @author cloudtour
     * @date 2023/3/20 22:47
     */
    private int getTeachplanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     * @description 找出下级课程计划
     * @param teachplan  课程信息
     * @return Teachplan 下级课程计划
     * @author cloudtour
     * @date 2023/3/22 19:57
     */
    private Teachplan getNextTeachplan(Teachplan teachplan){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,teachplan.getCourseId());
        queryWrapper.eq(Teachplan::getParentid,teachplan.getParentid());
        queryWrapper.eq(Teachplan::getOrderby,teachplan.getOrderby()+1);
        Teachplan res = teachplanMapper.selectOne(queryWrapper);
        return res;
    }

    /**
     * @description 找出上级课程计划
     * @param teachplan  课程信息
     * @return Teachplan 上级课程计划
     * @author cloudtour
     * @date 2023/3/22 20:30
     */
    private Teachplan getUpTeachplan(Teachplan teachplan){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,teachplan.getCourseId());
        queryWrapper.eq(Teachplan::getParentid,teachplan.getParentid());
        queryWrapper.eq(Teachplan::getOrderby,teachplan.getOrderby()-1);
        Teachplan res = teachplanMapper.selectOne(queryWrapper);
        return res;
    }
}
