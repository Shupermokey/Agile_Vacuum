package com.SE459.Portal.Entity;

public class Error {

    private int ErrorCode;
    private String Message;

    public Error(String errorMessage){
        this.Message = errorMessage;
    }

    public Error(int code, String errorMessage) {
        this.ErrorCode = code;
        this.Message = errorMessage;
    }

    public String getMessage() { return this.Message; }
    public void setMessage(String errorMessage) { this.Message = errorMessage; }

    public int getErrorCode() { return this.ErrorCode; }
    public void setErrorCode(int code) { this.ErrorCode = code; }
}
