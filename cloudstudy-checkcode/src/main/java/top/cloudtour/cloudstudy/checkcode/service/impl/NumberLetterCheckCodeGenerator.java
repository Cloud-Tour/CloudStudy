package top.cloudtour.cloudstudy.checkcode.service.impl;

import top.cloudtour.cloudstudy.checkcode.service.CheckCodeService;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author cloudtour
 * @version 1.0
 * @description 数字字母生成器
 * @date 2023/3/29 18:28
 */
@Component("NumberLetterCheckCodeGenerator")
public class NumberLetterCheckCodeGenerator implements CheckCodeService.CheckCodeGenerator {


    @Override
    public String generate(int length) {
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
