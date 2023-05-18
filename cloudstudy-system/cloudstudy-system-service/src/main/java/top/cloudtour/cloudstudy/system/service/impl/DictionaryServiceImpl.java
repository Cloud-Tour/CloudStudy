package top.cloudtour.cloudstudy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import top.cloudtour.cloudstudy.system.mapper.DictionaryMapper;

import top.cloudtour.cloudstudy.system.model.po.Dictionary;
import top.cloudtour.cloudstudy.system.service.DictionaryService;

import org.springframework.stereotype.Service;



import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {



    @Override
    public List<Dictionary> queryAll() {

        List<Dictionary> list = this.list();


        return list;
    }

    @Override
    public Dictionary getByCode(String code) {

        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dictionary::getCode, code);

        Dictionary dictionary = this.getOne(queryWrapper);


        return dictionary;
    }
}
