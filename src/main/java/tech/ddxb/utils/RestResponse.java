//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tech.ddxb.utils;

public class RestResponse<T> {
    public static final int SUCCESS_CODE = 0;
    public static final int FAIL_CODE = 1;
    private T result;
    private int code;
    private String msg;

    public RestResponse() {
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
