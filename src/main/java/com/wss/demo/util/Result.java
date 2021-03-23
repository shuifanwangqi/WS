package com.wss.demo.util;

public class Result<T> {
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private Integer code;
    private String msg;
    private String message;
    private T data;

    public Result success(T t){
        this.setCode(200);
        this.setMsg("ok");
        this.setMessage("成功");
        this.setData(t);
        return this;
    }

    public Result fail(String msg){
        this.setCode(1001);
        this.setMsg(msg);
        this.setMessage(msg);
        return this;
    }
}
