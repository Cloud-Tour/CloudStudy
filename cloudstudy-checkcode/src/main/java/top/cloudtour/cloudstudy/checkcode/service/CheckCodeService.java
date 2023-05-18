package top.cloudtour.cloudstudy.checkcode.service;

import top.cloudtour.cloudstudy.checkcode.model.CheckCodeParamsDto;
import top.cloudtour.cloudstudy.checkcode.model.CheckCodeResultDto;


/**
 * @author cloudtour
 * @version 1.0
 * @description 验证码接口
 * @date 2023/3/29 15:59
 */
public interface CheckCodeService {


    /**
     * @description 生成验证码
     * @param checkCodeParamsDto 生成验证码参数
     * @return top.cloudtour.cloudstudy.checkcode.model.CheckCodeResultDto 验证码结果
     * @author cloudtour
     * @date 2023/3/29 18:21
    */
     CheckCodeResultDto generate(CheckCodeParamsDto checkCodeParamsDto);

     /**
      * @description 校验验证码
      * @param key
      * @param code
      * @return boolean
      * @author cloudtour
      * @date 2023/3/29 18:46
     */
    public boolean verify(String key, String code);


    /**
     * @description 验证码生成器
     * @author cloudtour
     * @date 2023/3/29 16:34
    */
    public interface CheckCodeGenerator{
        /**
         * 验证码生成
         * @return 验证码
         */
        String generate(int length);
        

    }

    /**
     * @description key生成器
     * @author cloudtour
     * @date 2023/3/29 16:34
     */
    public interface KeyGenerator{

        /**
         * key生成
         * @return 验证码
         */
        String generate(String prefix);
    }


    /**
     * @description 验证码存储
     * @author cloudtour
     * @date 2023/3/29 16:34
     */
    public interface CheckCodeStore{

        /**
         * @description 向缓存设置key
         * @param key key
         * @param value value
         * @param expire 过期时间,单位秒
         * @return void
         * @author cloudtour
         * @date 2023/3/29 17:15
        */
        void set(String key, String value, Integer expire);

        String get(String key);

        void remove(String key);
    }
}
