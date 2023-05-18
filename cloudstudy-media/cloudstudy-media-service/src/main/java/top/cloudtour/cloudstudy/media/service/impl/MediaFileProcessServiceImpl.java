package top.cloudtour.cloudstudy.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cloudtour.cloudstudy.media.mapper.MediaFilesMapper;
import top.cloudtour.cloudstudy.media.mapper.MediaProcessHistoryMapper;
import top.cloudtour.cloudstudy.media.mapper.MediaProcessMapper;
import top.cloudtour.cloudstudy.media.model.po.MediaFiles;
import top.cloudtour.cloudstudy.media.model.po.MediaProcess;
import top.cloudtour.cloudstudy.media.model.po.MediaProcessHistory;
import top.cloudtour.cloudstudy.media.service.MediaFileProcessService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description TODO
 * @date 2023/3/24 21:17
 */
@Slf4j
@Service
public class MediaFileProcessServiceImpl implements MediaFileProcessService {
    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Autowired
    MediaProcessHistoryMapper mediaProcessHistoryMapper;


    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {
        List<MediaProcess> mediaProcesses = mediaProcessMapper.selectListByShardIndex(shardTotal, shardIndex, count);
        return mediaProcesses;
    }

    @Transactional
    @Override
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {
        //查出任务，如果不存在则直接返回
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if(mediaProcess == null){
            return ;
        }
        //处理失败，更新任务处理结果
        LambdaQueryWrapper<MediaProcess> queryWrapperById = new LambdaQueryWrapper<MediaProcess>().eq(MediaProcess::getId, taskId);
        if(status.equals("3")){
            MediaProcess mediaProcess_u = new MediaProcess();
            mediaProcess_u.setStatus("3");
            mediaProcess_u.setErrormsg(errorMsg);
            mediaProcessMapper.update(mediaProcess_u,queryWrapperById);
            return ;
        }

        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
        if(mediaFiles!=null){
            mediaFiles.setUrl(url);
            mediaFilesMapper.updateById(mediaFiles);
        }
        //处理成功，更新url和状态
        mediaProcess.setUrl(url);
        mediaProcess.setStatus("2");
        mediaProcess.setFinishDate(LocalDateTime.now());
        mediaProcessMapper.updateById(mediaProcess);

        //添加到历史记录
        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        //删除mediaProcess
        mediaProcessMapper.deleteById(mediaProcess.getId());

    }

    //开启任务-防止任务重复消费
    @Override
    public boolean startTask(Long taskId) {
        int result = mediaProcessMapper.startTask(taskId);
        return result<=0?false:true;
    }


}
