package com.geekskool.leger.Models;

/**
 * Created by manisharana on 27/12/16.
 */
public class Result {

    private boolean success;
    private String errorMsg;

    public Result(boolean success, String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
