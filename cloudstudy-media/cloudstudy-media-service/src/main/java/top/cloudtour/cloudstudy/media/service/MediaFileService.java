package top.cloudtour.cloudstudy.media.service;


import org.springframework.transaction.annotation.Transactional;
import top.cloudtour.cloudstudy.base.model.PageParams;
import top.cloudtour.cloudstudy.base.model.PageResult;
import top.cloudtour.cloudstudy.base.model.RestResponse;
import top.cloudtour.cloudstudy.media.model.dto.QueryMediaParamsDto;
import top.cloudtour.cloudstudy.media.model.dto.UploadFileParamsDto;
import top.cloudtour.cloudstudy.media.model.dto.UploadFileResultDto;
import top.cloudtour.cloudstudy.media.model.po.MediaFiles;

import java.io.File;

/**
 * @author cloudtour
 * @version 1.0
 * @description 媒资文件管理业务类
 * @date 2023/3/23 18:55
 */
public interface MediaFileService {

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return top.cloudtour.cloudstudy.base.model.PageResult<top.cloudtour.cloudstudy.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     * @author cloudtour
     * @date 2023/3/23 19:02
     */
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


    /**
     * @description 上传文件
     * @param uploadFileParamsDto  上传文件信息
     * @param folder  文件目录,如果不传则默认年、月、日
     * @return top.cloudtour.cloudstudy.media.model.dto.UploadFileResultDto 上传文件结果
     * @author cloudtour
     * @date 2023/3/23 19:31
     */
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes, String folder, String objectName);


    /**
     * @description 将文件信息添加到文件表
     * @param companyId  机构id
     * @param fileMd5  文件md5值
     * @param uploadFileParamsDto  上传文件的信息
     * @param bucket  桶
     * @param objectName 对象名称
     * @return top.cloudtour.cloudstudy.media.model.po.MediaFiles
     * @author cloudtour
     * @date 2023/3/23 20:44
     */
    @Transactional
    public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName);

    /**
     * @description 检查文件是否存在
     * @param fileMd5 文件的md5
     * @return top.cloudtour.cloudstudy.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @author cloudtour
     * @date 2023/3/23 21:44
     */
    public RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * @description 检查分块是否存在
     * @param fileMd5  文件的md5
     * @param chunkIndex  分块序号
     * @return top.cloudtour.cloudstudy.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @author cloudtour
     * @date 2023/3/23 21:45
     */
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /**
     * @description 上传分块
     * @param fileMd5  文件md5
     * @param chunk  分块序号
     * @param bytes  文件字节
     * @return top.cloudtour.cloudstudy.base.model.RestResponse
     * @author cloudtour
     * @date 2023/3/23 21:55
     */
    public RestResponse uploadChunk(String fileMd5,int chunk,byte[] bytes);

    /**
     * @description 合并分块
     * @param companyId  机构id
     * @param fileMd5  文件md5
     * @param chunkTotal 分块总和
     * @param uploadFileParamsDto 文件信息
     * @return top.cloudtour.cloudstudy.base.model.RestResponse
     * @author cloudtour
     * @date 2023/3/23 22:01
     */
    public RestResponse mergechunks(Long companyId,String fileMd5,int chunkTotal,UploadFileParamsDto uploadFileParamsDto);


    /**
     * @description 根据id查询文件信息
     * @param id  文件id
     * @return top.cloudtour.cloudstudy.media.model.po.MediaFiles 文件信息
     * @author cloudtour
     * @date 2023/3/24 16:55
     */
    public MediaFiles getFileById(String id);

    //根据桶和文件路径从minio下载文件
    public File downloadFileFromMinIO(File file, String bucket, String objectName);

    public void addMediaFilesToMinIO(String filePath, String bucket, String objectName);

}
