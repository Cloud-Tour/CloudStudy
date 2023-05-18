package top.cloudtour.cloudstudy.service;

import top.cloudtour.cloudstudy.model.dto.CoursePreviewDto;

import java.io.File;

public interface CoursePublishService {
    /**
     * @description 获取课程预览信息
     * @param courseId 课程id
     * @return top.cloudtour.cloudstudy.model.dto.CoursePreviewDto
     * @author cloudtour
     * @date 2023/3/26 14:29
     */
    public CoursePreviewDto getCoursePreviewInfo(long courseId);


    /**
     * @description 提交审核
     * @param courseId  课程id
     * @return void
     * @author cloudtour
     * @date 2023/3/26 23:59
     */
    public void commitAudit(Long companyId,Long courseId);

    /**
     * @description 课程发布接口
     * @param companyId 机构id
     * @param courseId 课程id
     * @return void
     * @author cloudtour
     * @date 2023/3/27 10:14
     */
    public void publish(Long companyId,Long courseId);

    /**
     * @description 课程静态化
     * @param courseId  课程id
     * @return File 静态化文件
     * @author cloudtour
     * @date 2023/3/27 20:28
     */
    public File generateCourseHtml(Long courseId);
    /**
     * @description 上传课程静态化页面
     * @param file  静态化文件
     * @return void
     * @author cloudtour
     * @date 2023/3/27 10:29
     */
    public void  uploadCourseHtml(Long courseId,File file);

    //创建es索引
    public Boolean saveCourseIndex(Long courseId) ;

}
