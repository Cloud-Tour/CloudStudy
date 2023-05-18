package top.cloudtour.cloudstudy.media.service;

import top.cloudtour.cloudstudy.media.model.po.MediaProcess;

import java.util.List;

/**
 * @author cloudstudy
 * @version 1.0
 * @description 媒资文件处理业务方法
 * @date 2023/3/24 21:15
 */
public interface MediaFileProcessService {

    /**
     * @description 获取待处理任务
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param count 获取记录数
     * @return java.util.List<top.cloudtour.cloudstudy.media.model.po.MediaProcess>
     * @author cloudstudy
     * @date 2023/3/24 21:15
     */
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);

    /**
     * @description 保存任务结果
     * @param taskId  任务id
     * @param status 任务状态
     * @param fileId  文件id
     * @param url url
     * @param errorMsg 错误信息
     * @return void
     * @author cloudstudy
     * @date 2023/3/24 21:18
     */
    void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);


    //开启任务-防止任务重复消费
    boolean startTask(Long taskId);
}

