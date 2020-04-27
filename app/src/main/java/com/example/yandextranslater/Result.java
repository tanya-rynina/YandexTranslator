package com.example.yandextranslater;

public class Result {
    private String[] text;
    private Exception exception;
    private int code;
    private String lang;


    public Result() {
    }

    public boolean hasError(){
        return exception!= null;
    }



    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
