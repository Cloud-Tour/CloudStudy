package top.cloudtour.cloudstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cloudtour.cloudstudy.base.execption.CloudStudyException;
import top.cloudtour.cloudstudy.base.model.PageParams;
import top.cloudtour.cloudstudy.base.model.PageResult;
import top.cloudtour.cloudstudy.mapper.*;
import top.cloudtour.cloudstudy.model.dto.AddCourseDto;
import top.cloudtour.cloudstudy.model.dto.CourseBaseInfoDto;
import top.cloudtour.cloudstudy.model.dto.EditCourseDto;
import top.cloudtour.cloudstudy.model.dto.QueryCourseParamsDto;
import top.cloudtour.cloudstudy.model.po.*;
import top.cloudtour.cloudstudy.service.CourseBaseInfoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程信息管理业务接口实现类
 * @date 2023/3/16 21:04
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Autowired
    CourseMarketServiceImpl courseMarketService;

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    CourseTeacherMapper courseTeacherMapper;


    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {


        //构建查询条件对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //构建查询条件，根据课程名称查询
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        //构建查询条件，根据课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        //构建查询条件，根据课程发布状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());

        //分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<CourseBase> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;


    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        //对参数进行合法性的校验
        //合法性校验
        if (StringUtils.isBlank(dto.getName())) {
            throw new CloudStudyException("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new CloudStudyException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new CloudStudyException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new CloudStudyException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new CloudStudyException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new CloudStudyException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new CloudStudyException("收费规则为空");
        }
        //对数据进行封装，调用mapper进行数据持久化
        CourseBase courseBase = new CourseBase();
        //将传入dto的数据设置到 courseBase对象
//        courseBase.setName(dto.getName());
//        courseBase.setMt(dto.getMt());
//        courseBase.setSt(dto.getSt());
        //将dto中和courseBase属性名一样的属性值拷贝到courseBase
        BeanUtils.copyProperties(dto,courseBase);
        //设置机构id
        courseBase.setCompanyId(companyId);
        //创建时间
        courseBase.setCreateDate(LocalDateTime.now());
        //审核状态默认为未提交
        courseBase.setAuditStatus("202002");
        //发布状态默认为未发布
        courseBase.setStatus("203001");
        //课程基本表插入一条记录
        int insert = courseBaseMapper.insert(courseBase);
        //获取课程id
        Long courseId = courseBase.getId();
        CourseMarket courseMarket = new CourseMarket();
        //将dto中和courseMarket属性名一样的属性值拷贝到courseMarket
        BeanUtils.copyProperties(dto,courseMarket);
        courseMarket.setId(courseId);
        //校验如果课程为收费，价格必须输入
        String charge = dto.getCharge();
        if("201001".equals(charge)){//收费
            if(courseMarket.getPrice() == null || courseMarket.getPrice().floatValue()<=0){
                throw new CloudStudyException("课程为收费但是价格为空");
            }
        }

        //向课程营销表插入一条记录
        int insert1 = courseMarketMapper.insert(courseMarket);

        if(insert<=0|| insert1<=0){
            throw new CloudStudyException("添加课程失败");
        }

        //组装要返回的结果
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }

    /**
     * 根据课程id查询课程的基本和营销信息
     * @param courseId 课程id
     * @return 课程的信息
     */
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(long courseId){

        //基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);

        //营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if (courseMarket!=null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //根据课程分类的id查询分类的名称
        String mt = courseBase.getMt();
        String st = courseBase.getSt();

        CourseCategory mtCategory = courseCategoryMapper.selectById(mt);
        CourseCategory stCategory = courseCategoryMapper.selectById(st);
        if(mtCategory!=null){
            //分类名称
            String mtName = mtCategory.getName();
            courseBaseInfoDto.setMtName(mtName);
        }
        if(stCategory!=null){
            //分类名称
            String stName = stCategory.getName();
            courseBaseInfoDto.setStName(stName);
        }


        return courseBaseInfoDto;

    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        //业务规则校验，本机构只允许修改本机构的课程
        //课程id
        Long courseId = dto.getId();
        CourseBase courseBase_u = courseBaseMapper.selectById(courseId);
        if(courseBase_u==null){
            CloudStudyException.cast("课程信息不存在");
        }
        if(!companyId.equals(courseBase_u.getCompanyId())){
            CloudStudyException.cast("本机构只允许修改本机构的课程");
        }

        //封装数据
        //将请求参数拷贝到待修改对象中
        BeanUtils.copyProperties(dto,courseBase_u);
        courseBase_u.setChangeDate(LocalDateTime.now());
        //更新到数据库
        int i = courseBaseMapper.updateById(courseBase_u);

        //封装营销信息的数据
        CourseMarket courseMarket = new CourseMarket();

        //将dto中的课程营销信息拷贝至courseMarket对象中
        BeanUtils.copyProperties(dto,courseMarket);
        saveCourseMarket(courseMarket);

        return getCourseBaseInfo(courseId);
    }

    @Override
    @Transactional
    public void deleteCourseBase(Long companyId, Long courseId) {
        //业务规则校验，本机构只允许修改本机构的课程
        //课程id
        CourseBase courseBase_u = courseBaseMapper.selectById(courseId);
        if(courseBase_u==null){
            CloudStudyException.cast("课程信息不存在");
        }
        if(!companyId.equals(courseBase_u.getCompanyId())){
            CloudStudyException.cast("本机构只允许修改本机构的课程");
        }
        if (!"202002".equals(courseBase_u.getAuditStatus())){
            CloudStudyException.cast("已审核的课程无法删除");
        }
        //删除基本信息、课程计划、课程教师信息
        courseBaseMapper.deleteById(courseId);
        //删除营销信息
        courseMarketMapper.deleteById(courseId);
        //删除课程计划
        LambdaQueryWrapper<Teachplan> tpWrapper = new LambdaQueryWrapper<>();
        tpWrapper.eq(Teachplan::getCourseId,courseId);
        teachplanMapper.delete(tpWrapper);
        //删除课程教师
        LambdaQueryWrapper<CourseTeacher> ctWrapper = new LambdaQueryWrapper<>();
        ctWrapper.eq(CourseTeacher::getCourseId,courseId);
        courseTeacherMapper.delete(ctWrapper);

    }

    //抽取对营销的保存
    private int saveCourseMarket(CourseMarket courseMarket){


        String charge = courseMarket.getCharge();
        if(StringUtils.isBlank(charge)){
            CloudStudyException.cast("收费规则没有选择");
        }
        if(charge.equals("201001")){
            if(courseMarket.getPrice()==null || courseMarket.getPrice().floatValue()<=0){
                CloudStudyException.cast("课程为收费价格不能为空且必须大于0");

            }
        }

        //保存
        boolean b = courseMarketService.saveOrUpdate(courseMarket);

        return b?1:0;

    }
}
