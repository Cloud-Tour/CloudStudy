package com.zkd.cloudStudy.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.vod.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SubjectService extends IService<Subject> {

    //查询下一层课程分类
    List<Subject> selectList(Long id);

    //导出
    void exportData(HttpServletResponse response);

    //导入
    void importDictData(MultipartFile file);
}
