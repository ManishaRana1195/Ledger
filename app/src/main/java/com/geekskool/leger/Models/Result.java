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
}
