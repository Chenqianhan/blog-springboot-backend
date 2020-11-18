package com.quainhanhub.vue_blog.common.lang;

import lombok.Data;
import java.io.Serializable;

@Data
public class Result implements Serializable{

    private int code; //200: succeed, 400: Exception
    private String msg;
    private Object data;

    //Encapsulate common function that controllers may use. And we need to make it static.
    public static Result result(int code, String msg, Object data){
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    public static Result success(Object data){
        return result(200, "Success", data);
    }

    public static Result fail(String msg){
        return result(400, msg, null);
    }

    public static Result fail(int code, String msg){
        return result(code, msg, null);
    }

    public static Result fail(String msg, Object data){
        return result(400, msg, data);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
