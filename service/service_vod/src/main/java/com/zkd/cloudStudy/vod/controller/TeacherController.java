package com.zkd.cloudStudy.vod.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkd.cloudStudy.model.vod.Teacher;
import com.zkd.cloudStudy.vo.vod.TeacherQueryVo;
import com.zkd.cloudStudy.vod.service.TeacherService;
import com.zkd.cloudStudy.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "讲师管理接口")
@RestController
@RequestMapping(value="/admin/vod/teacher")
//@CrossOrigin //跨域
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //查询所有讲师列表
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public Result findAll(){
        List<Teacher> res = teacherService.list();
        return Result.ok(res);
    }

    //删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeById(@ApiParam(name = "id", value = "ID", required = true) @PathVariable String id){
        boolean res = teacherService.removeById(id);
        if (res){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    //条件查询分页列表
    @ApiOperation("条件查询分页列表")
    @PostMapping("/findQueryPage/{page}/{limit}")
    public Result pageFindAll( @ApiParam(name = "page", value = "当前页码", required = true)
                                   @PathVariable Long page,
                               @ApiParam(name = "limit", value = "每页记录数", required = true)
                                   @PathVariable Long limit,
                               @ApiParam(name = "teacherVo", value = "查询对象", required = false)
                                   @RequestBody(required = false) TeacherQueryVo teacherQueryVo){

        Page<Teacher> pageParam = new Page<>(page, limit);
        //获取条件值
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        Integer level = teacherQueryVo.getLevel();
        String name = teacherQueryVo.getName();
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(joinDateBegin)){
            wrapper.eq("join_date",joinDateBegin);
        }
        if (!StringUtils.isEmpty(joinDateEnd)){
            wrapper.eq("join_date",joinDateEnd);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        IPage<Teacher> res = teacherService.page(pageParam, wrapper);
        return Result.ok(res);
    }

    //增加讲师接口
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result save(@RequestBody Teacher teacher){
        boolean res = teacherService.save(teacher);
        if (res){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    //根据id查询
    @ApiOperation("根据id查询")
    @GetMapping("getTeacher/{id}")
    public Result getById(@PathVariable Long id){
        Teacher res = teacherService.getById(id);
        return Result.ok(res);
    }

    //修改
    @ApiOperation("修改教师")
    @PostMapping("updateTeacher")
    public Result updateById(@RequestBody Teacher teacher){
        boolean res = teacherService.updateById(teacher);
        if (res){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    //批量删除讲师
    @ApiOperation("批量删除讲师")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean res = teacherService.removeByIds(idList);
        if (res){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    @ApiOperation("根据id查询")
    @GetMapping("inner/getTeacher/{id}")
    public Teacher getTeacherLive(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return teacher;
    }
}

