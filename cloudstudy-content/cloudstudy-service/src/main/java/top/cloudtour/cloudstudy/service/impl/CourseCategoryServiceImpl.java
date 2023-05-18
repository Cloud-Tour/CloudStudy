package top.cloudtour.cloudstudy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import top.cloudtour.cloudstudy.mapper.CourseCategoryMapper;
import top.cloudtour.cloudstudy.model.dto.CourseCategoryTreeDto;
import top.cloudtour.cloudstudy.service.CourseCategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程类别字典服务实现类
 * @date 2023/3/17 20:58
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //查询数据库得到的课程分类
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        //最终返回的列表
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();
        HashMap<String, CourseCategoryTreeDto> mapTemp = new HashMap<>();

        courseCategoryTreeDtos.stream().forEach(item->{
            mapTemp.put(item.getId(),item);
            //只将根节点的下级节点放入list
            if(item.getParentid().equals(id)){
                categoryTreeDtos.add(item);
            }
            //查出父节点
            CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
            if(courseCategoryTreeDto!=null){
                //若父节点的子树未创建则先创建
                if(courseCategoryTreeDto.getChildrenTreeNodes() ==null){
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //向节点的下级节点list加入节点
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }

        });

        return categoryTreeDtos;

    }
}
