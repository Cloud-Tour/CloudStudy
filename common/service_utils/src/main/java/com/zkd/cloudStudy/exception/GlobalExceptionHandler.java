package com.zkd.cloudStudy.exception;


import com.zkd.cloudStudy.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice  //aop
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        System.out.println("全局.....");
        e.printStackTrace();
        return Result.fail(null).message("执行全局异常处理");
    }

    //特定异常处理ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        System.out.println("特定.....");
        e.printStackTrace();
        return Result.fail(null).message("执行ArithmeticException异常处理");
    }

    //自定义异常处理GgktException
    @ExceptionHandler(com.zkd.cloudStudy.exception.GgktException.class)
    @ResponseBody
    public Result error(com.zkd.cloudStudy.exception.GgktException e) {
        e.printStackTrace();
        return Result.fail(null).code(e.getCode()).message(e.getMsg());
    }

}