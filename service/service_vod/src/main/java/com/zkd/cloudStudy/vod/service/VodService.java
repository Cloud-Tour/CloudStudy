package com.zkd.cloudStudy.vod.service;

import java.io.InputStream;
import java.util.Map;

public interface VodService {
    //上传视频
    String uploadVideo(InputStream inputStream, String originalFilename);

    //删除视频
    void removeVideo(String videoSourceId);

    //获取视频播放凭证
    Map<String,Object> getPlayAuth(Long courseId, Long videoId);
}
