package com.jason.mytest.example;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 所有根据等于拼接查询的条件
 * 一般要作为查询条件也要作为返回结果
 */
@Getter
@Setter
public class LogDTO implements Serializable {


    /**
     * 应用
     */
    private String app;

    /**
     * 实体
     */
    private String entity;

    /**
     * 表id
     */
    private String bid;

    /**
     * 动作
     */
    private String action;

    /**
     * 认证方式
     */
    private String auth;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 用户名字
     */
    private String unm;

    /**
     * ip
     */
    private String ip;
}
