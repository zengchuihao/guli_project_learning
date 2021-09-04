package com.zengchuihao.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCreateException extends RuntimeException {

    private Integer code;
    private String msg;




}
