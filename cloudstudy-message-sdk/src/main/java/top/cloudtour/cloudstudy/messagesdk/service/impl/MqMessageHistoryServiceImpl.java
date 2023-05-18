package top.cloudtour.cloudstudy.messagesdk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.cloudtour.cloudstudy.messagesdk.mapper.MqMessageHistoryMapper;
import top.cloudtour.cloudstudy.messagesdk.model.po.MqMessageHistory;
import top.cloudtour.cloudstudy.messagesdk.service.MqMessageHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cloudtour
 */
@Slf4j
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory> implements MqMessageHistoryService {

}
