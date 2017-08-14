package com.wang.out.common;

import java.util.HashMap;


/**
 * @包名 com.wang.out.common
 * @类名: ResultPojo
 * @描述: 接口返回对象实体类
 * @作者： 王世林
 * @修改人：xxx
 * @修改时间:xxxx-xx-xx
 */
public class ResultPojo {

    private int status = 200;
    private Object data = new HashMap<>();


    public ResultPojo() {
    }

    public ResultPojo(int status) {
        this.status = status;

    }

    public ResultPojo(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ResultPojo(int status, Object... args) {
        this.status = status;

    }

    public ResultPojo(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}

