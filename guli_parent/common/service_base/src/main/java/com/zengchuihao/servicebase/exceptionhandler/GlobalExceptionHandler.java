package com.zengchuihao.servicebase.exceptionhandler;


import com.zengchuihao.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());

        return R.error().message("执行了全局的异常处理");
    }

    @ResponseBody
    @ExceptionHandler(MyCreateException.class)
    public R error1(MyCreateException e) {
        //log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }

}
