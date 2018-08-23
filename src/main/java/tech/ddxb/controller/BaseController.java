package tech.ddxb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import tech.ddxb.utils.RestResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leahhuang on 2018/6/17.
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseController() {
    }

    protected <T> RestResponse<T> getSuccessResponse(T data) {
        return this.buildResponse(0, "请求成功", data);
    }

    protected <T> RestResponse<T> getFailResponse(String msg) {
        return this.buildResponse(-1, msg, (T) null);
    }

    protected <T> RestResponse<T> buildResponse(int code, String msg, T data) {
        RestResponse<T> response = new RestResponse();
        response.setResult(data);
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

}
