package com.ahba1.homework.pojo;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result implements Serializable {
    /**
     * 返回代码
     */
    private int status;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public final static int SUCCESS = 200;
    public final static int SERVER_ERROR = 500;
    public final static int CLIENT_ERROR = 400;
}
