package com.zkd.cloudStudy.live.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkd.cloudStudy.client.course.CourseFeignClient;
import com.zkd.cloudStudy.client.user.UserInfoFeignClient;
import com.zkd.cloudStudy.live.mapper.LiveCourseMapper;
import com.zkd.cloudStudy.live.mtcloud.CommonResult;
import com.zkd.cloudStudy.live.mtcloud.MTCloud;
import com.zkd.cloudStudy.live.service.*;
import com.zkd.cloudStudy.model.live.*;
import com.zkd.cloudStudy.model.user.UserInfo;
import com.zkd.cloudStudy.model.vod.Teacher;
import com.zkd.cloudStudy.vo.live.LiveCourseConfigVo;
import com.zkd.cloudStudy.vo.live.LiveCourseFormVo;
import com.zkd.cloudStudy.vo.live.LiveCourseGoodsView;
import com.zkd.cloudStudy.vo.live.LiveCourseVo;
import com.zkd.cloudStudy.exception.GgktException;
import com.zkd.cloudStudy.utils.DateUtil;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements LiveCourseService {

    @Resource
    private LiveCourseAccountService liveCourseAccountService;

    @Resource
    private LiveCourseDescriptionService liveCourseDescriptionService;

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private CourseFeignClient teacherFeignClient;

    @Autowired
    private LiveCourseConfigService liveCourseConfigService;

    @Autowired
    private LiveCourseGoodsService liveCourseGoodsService;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    @Resource
    private MTCloud mtCloudClient;

    //????????????????????????
    @Override
    public IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam) {
        IPage<LiveCourse> page = baseMapper.selectPage(pageParam, null);
        List<LiveCourse> liveCourseList = page.getRecords();

        for(LiveCourse liveCourse : liveCourseList) {
            Teacher teacher = courseFeignClient.getTeacherLive(liveCourse.getTeacherId());
            liveCourse.getParam().put("teacherName", teacher.getName());
            liveCourse.getParam().put("teacherLevel", teacher.getLevel());
        }
        return page;
    }

    @SneakyThrows
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Boolean save(LiveCourseFormVo liveCourseFormVo) {
        LiveCourse liveCourse = new LiveCourse();
        BeanUtils.copyProperties(liveCourseFormVo, liveCourse);

        Teacher teacher = teacherFeignClient.getTeacherLive(liveCourseFormVo.getTeacherId());
        HashMap<Object, Object> options = new HashMap<>();
        options.put("scenes", 2);//???????????????1: ???????????????2: ????????????????????? 1???????????????????????????????????????????????????
        options.put("password", liveCourseFormVo.getPassword());
        String res = mtCloudClient.courseAdd(liveCourse.getCourseName(), teacher.getId().toString(), new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"), new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"), teacher.getName(), teacher.getIntro(), options);

        System.out.println("return:: "+res);
        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
            JSONObject object = commonResult.getData();
            liveCourse.setCourseId(object.getLong("course_id"));
            baseMapper.insert(liveCourse);

            //????????????????????????
            LiveCourseDescription liveCourseDescription = new LiveCourseDescription();
            liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
            liveCourseDescription.setLiveCourseId(liveCourse.getId());
            liveCourseDescriptionService.save(liveCourseDescription);

            //????????????????????????
            LiveCourseAccount liveCourseAccount = new LiveCourseAccount();
            liveCourseAccount.setLiveCourseId(liveCourse.getId());
            liveCourseAccount.setZhuboAccount(object.getString("bid"));
            liveCourseAccount.setZhuboPassword(liveCourseFormVo.getPassword());
            liveCourseAccount.setAdminKey(object.getString("admin_key"));
            liveCourseAccount.setUserKey(object.getString("user_key"));
            liveCourseAccount.setZhuboKey(object.getString("zhubo_key"));
            liveCourseAccountService.save(liveCourseAccount);
        } else {
            String getmsg = commonResult.getmsg();
            throw new GgktException(20001,getmsg);
        }
        return true;
    }

    @Override
    public void removeLive(Long id) {
        //??????id????????????????????????
        LiveCourse liveCourse = baseMapper.selectById(id);
        if(liveCourse != null) {
            //????????????courseid
            Long courseId = liveCourse.getCourseId();
            try {
                //????????????????????????????????????
                mtCloudClient.courseDelete(courseId.toString());
                //???????????????
                baseMapper.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GgktException(20001,"????????????????????????");
            }
        }
    }

    //??????
    @Override
    public void updateById(LiveCourseFormVo liveCourseFormVo) {
        //??????id??????????????????????????????
        LiveCourse liveCourse = baseMapper.selectById(liveCourseFormVo.getId());
        BeanUtils.copyProperties(liveCourseFormVo,liveCourse);
        //??????
        Teacher teacher =
                teacherFeignClient.getTeacherLive(liveCourseFormVo.getTeacherId());

//             *   course_id ??????ID
//     *   account ?????????????????????????????????
//     *   course_name ????????????
//     *   start_time ??????????????????,??????:2015-01-01 12:00:00
//                *   end_time ??????????????????,??????:2015-01-01 13:00:00
//                *   nickname 	???????????????
//                *   accountIntro 	???????????????
//                *  options 		????????????
        HashMap<Object, Object> options = new HashMap<>();
        try {
            String res = mtCloudClient.courseUpdate(liveCourse.getCourseId().toString(),
                    teacher.getId().toString(),
                    liveCourse.getCourseName(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getIntro(),
                    options);
            //???????????????????????????????????????
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                //??????????????????????????????
                liveCourse.setCourseId(object.getLong("course_id"));
                baseMapper.updateById(liveCourse);
                //??????????????????????????????
                LiveCourseDescription liveCourseDescription =
                        liveCourseDescriptionService.getLiveCourseById(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                liveCourseDescriptionService.updateById(liveCourseDescription);
            } else {
                throw new GgktException(20001,"????????????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LiveCourseFormVo getLiveCourseFormVo(Long id) {
        LiveCourse liveCourse = this.getById(id);
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(id);

        LiveCourseFormVo liveCourseFormVo = new LiveCourseFormVo();
        BeanUtils.copyProperties(liveCourse, liveCourseFormVo);
        liveCourseFormVo.setDescription(liveCourseDescription.getDescription());
        return liveCourseFormVo;
    }

    @Override
    public LiveCourseConfigVo getCourseConfig(Long id) {
        LiveCourseConfigVo liveCourseConfigVo = new LiveCourseConfigVo();
        LiveCourseConfig liveCourseConfig = liveCourseConfigService.getByLiveCourseId(id);
        if(null != liveCourseConfig) {
            List<LiveCourseGoods> liveCourseGoodsList = liveCourseGoodsService.findByLiveCourseId(id);
            BeanUtils.copyProperties(liveCourseConfig, liveCourseConfigVo);
            liveCourseConfigVo.setLiveCourseGoodsList(liveCourseGoodsList);
        }
        return liveCourseConfigVo;
    }

    @Override
    public void updateConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourseConfig liveCourseConfigUpt = new LiveCourseConfig();
        BeanUtils.copyProperties(liveCourseConfigVo, liveCourseConfigUpt);
        if(null == liveCourseConfigVo.getId()) {
            liveCourseConfigService.save(liveCourseConfigUpt);
        } else {
            liveCourseConfigService.updateById(liveCourseConfigUpt);
        }
        liveCourseGoodsService.remove(new LambdaQueryWrapper<LiveCourseGoods>().eq(LiveCourseGoods::getLiveCourseId, liveCourseConfigVo.getLiveCourseId()));
        if(!CollectionUtils.isEmpty(liveCourseConfigVo.getLiveCourseGoodsList())) {
            liveCourseGoodsService.saveBatch(liveCourseConfigVo.getLiveCourseGoodsList());
        }
        this.updateLifeConfig(liveCourseConfigVo);
    }

    @Override
    public List<LiveCourseVo> findLatelyList() {
        List<LiveCourseVo> liveCourseVoList = baseMapper.findLatelyList();

        for(LiveCourseVo liveCourseVo : liveCourseVoList) {
            liveCourseVo.setStartTimeString(new DateTime(liveCourseVo.getStartTime()).toString("yyyy???MM???dd HH:mm"));
            liveCourseVo.setEndTimeString(new DateTime(liveCourseVo.getEndTime()).toString("HH:mm"));

            Long teacherId = liveCourseVo.getTeacherId();
            Teacher teacher = teacherFeignClient.getTeacherLive(teacherId);
            liveCourseVo.setTeacher(teacher);

            liveCourseVo.setLiveStatus(this.getLiveStatus(liveCourseVo));
        }
        return liveCourseVoList;
    }

    @SneakyThrows
    @Override
    public JSONObject getPlayAuth(Long id, Long userId) {
        LiveCourse liveCourse = this.getById(id);
        UserInfo userInfo = userInfoFeignClient.getById(userId);
        HashMap<Object,Object> options = new HashMap<Object, Object>();
        String res = mtCloudClient.courseAccess(liveCourse.getCourseId().toString(), userId.toString(), userInfo.getNickName(), MTCloud.ROLE_USER, 80*80*80, options);
        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
            JSONObject object = commonResult.getData();
            System.out.println("access::"+object.getString("access_token"));
            return object;
        } else {
            throw new GgktException(20001,"????????????");
        }
    }

    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        LiveCourse liveCourse = this.getById(courseId);
        liveCourse.getParam().put("startTimeString", new DateTime(liveCourse.getStartTime()).toString("yyyy???MM???dd HH:mm"));
        liveCourse.getParam().put("endTimeString", new DateTime(liveCourse.getEndTime()).toString("yyyy???MM???dd HH:mm"));
        Teacher teacher = teacherFeignClient.getTeacherLive(liveCourse.getTeacherId());
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(courseId);

        Map<String, Object> map = new HashMap<>();
        map.put("liveCourse", liveCourse);
        map.put("liveStatus", this.getLiveStatus(liveCourse));
        map.put("teacher", teacher);
        if(null != liveCourseDescription) {
            map.put("description", liveCourseDescription.getDescription());
        } else {
            map.put("description", "");
        }
        return map;
    }

    /**
     * ???????????? 0???????????? 1???????????? 2???????????????
     * @param liveCourse
     * @return
     */
    private int getLiveStatus(LiveCourse liveCourse) {
        // ???????????? 0???????????? 1???????????? 2???????????????
        int liveStatus = 0;
        Date curTime = new Date();
        if(DateUtil.dateCompare(curTime, liveCourse.getStartTime())) {
            liveStatus = 0;
        } else if(DateUtil.dateCompare(curTime, liveCourse.getEndTime())) {
            liveStatus = 1;
        } else {
            liveStatus = 2;
        }
        return liveStatus;
    }

    /**
     * ??????????????????
     * @param liveCourseConfigVo
     */
    @SneakyThrows
    private void updateLifeConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourse liveCourse = this.getById(liveCourseConfigVo.getLiveCourseId());

        //????????????
        HashMap<Object,Object> options = new HashMap<Object, Object>();
        //????????????
        options.put("pageViewMode", liveCourseConfigVo.getPageViewMode());
        //??????????????????
        JSONObject number = new JSONObject();
        number.put("enable", liveCourseConfigVo.getNumberEnable());
        options.put("number", number.toJSONString());
        //??????????????????
        JSONObject store = new JSONObject();
        number.put("enable", liveCourseConfigVo.getStoreEnable());
        number.put("type", liveCourseConfigVo.getStoreType());
        options.put("store", number.toJSONString());
        //????????????
        List<LiveCourseGoods> liveCourseGoodsList = liveCourseConfigVo.getLiveCourseGoodsList();
        if(!CollectionUtils.isEmpty(liveCourseGoodsList)) {
            List<LiveCourseGoodsView> liveCourseGoodsViewList = new ArrayList<>();
            for(LiveCourseGoods liveCourseGoods : liveCourseGoodsList) {
                LiveCourseGoodsView liveCourseGoodsView = new LiveCourseGoodsView();
                BeanUtils.copyProperties(liveCourseGoods, liveCourseGoodsView);
                liveCourseGoodsViewList.add(liveCourseGoodsView);
            }
            JSONObject goodsListEdit = new JSONObject();
            goodsListEdit.put("status", "0");
            options.put("goodsListEdit ", goodsListEdit.toJSONString());
            options.put("goodsList", JSON.toJSONString(liveCourseGoodsViewList));
        }

        String res = mtCloudClient.courseUpdateLifeConfig(liveCourse.getCourseId().toString(), options);

        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if(Integer.parseInt(commonResult.getCode()) != MTCloud.CODE_SUCCESS) {
            throw new GgktException(20001,"????????????????????????");
        }
    }
}
