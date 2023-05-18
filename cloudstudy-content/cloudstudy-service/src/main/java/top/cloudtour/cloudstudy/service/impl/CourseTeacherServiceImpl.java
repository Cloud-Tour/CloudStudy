package top.cloudtour.cloudstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cloudtour.cloudstudy.base.execption.CloudStudyException;
import top.cloudtour.cloudstudy.mapper.CourseTeacherMapper;
import top.cloudtour.cloudstudy.model.po.CourseTeacher;
import top.cloudtour.cloudstudy.service.CourseTeacherService;

import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 教师管理service接口实现类
 * @date 2023/3/21 20:44
 */
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacher> getTeachers(Long id) {
        LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseTeacher::getCourseId,id);
        List<CourseTeacher> res = courseTeacherMapper.selectList(wrapper);
        return res;
    }

    @Override
    @Transactional
    public CourseTeacher addTeacher(CourseTeacher teacher) {

        Long id = teacher.getId();
        if (id==null){
            LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CourseTeacher::getCourseId,teacher.getCourseId());
            wrapper.eq(CourseTeacher::getTeacherName,teacher.getTeacherName());
            Integer count = courseTeacherMapper.selectCount(wrapper);
            if (count>0){
                throw new CloudStudyException("在本课程中该教师已存在");
            }
            courseTeacherMapper.insert(teacher);
            CourseTeacher res = courseTeacherMapper.selectOne(wrapper);
            return res;
        }else {
            courseTeacherMapper.updateById(teacher);
            return teacher;
        }
    }

    @Override
    public void deleteTeacher(Long courseId, Long id) {
        LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseTeacher::getCourseId,courseId);
        wrapper.eq(CourseTeacher::getId,id);
        courseTeacherMapper.delete(wrapper);
    }
}
