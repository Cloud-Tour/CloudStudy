package top.cloudtour.cloudstudy.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cloudtour.cloudstudy.model.dto.BindTeachplanMediaDto;
import top.cloudtour.cloudstudy.model.dto.SaveTeachplanDto;
import top.cloudtour.cloudstudy.model.dto.TeachplanDto;
import top.cloudtour.cloudstudy.service.TeachplanService;

import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程计划编辑接口
 * @date 2023/3/20 21:34
 */
@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
public class TeachplanController {

    @Autowired
    TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程基础Id值",required = true,dataType = "Long",paramType = "path")
    @GetMapping("teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplayTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("删除课程计划")
    @DeleteMapping("teachplan/{id}")
    public void deleteTeachplan(@PathVariable Long id){
        teachplanService.deleteTeachplan(id);
    }

    @ApiOperation("向下移动课程计划")
    @PostMapping("teachplan/movedown/{id}")
    public void movedownTeachplan(@PathVariable Long id){
        teachplanService.movedownTeachplan(id);
    }

    @ApiOperation("向上移动课程计划")
    @PostMapping("teachplan/moveup/{id}")
    public void moveupTeachplan(@PathVariable Long id){
        teachplanService.moveupTeachplan(id);
    }


    @ApiOperation(value = "课程计划和媒资信息绑定")
    @PostMapping("/teachplan/association/media")
    public void associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto){
        teachplanService.associationMedia(bindTeachplanMediaDto);
    }

    @ApiOperation(value = "删除课程计划和媒资信息绑定")
    @DeleteMapping("/teachplan/association/media/{teachPlanId}/{mediaId}")
    public void associationMedia(@PathVariable long teachPlanId,
                                 @PathVariable String mediaId){
        teachplanService.DeleteAssociationMedia(teachPlanId,mediaId);
    }

}