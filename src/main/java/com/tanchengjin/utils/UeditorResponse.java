package com.tanchengjin.utils;

import java.io.Serializable;

/**
 * 适用与百度Ueditor富文本相应体
 * @author tanchengjin
 * @since v1.0.0
 */
public class UeditorResponse implements Serializable {
    private String state;
    private String url;
    private String title;
    private String original;
    private String type;
    private String size;

    private UeditorResponse(String state, String url, String title, String original, String type, String size) {
        this.state = state;
        this.url = url;
        this.title = title;
        this.original = original;
        this.type = type;
        this.size = size;
    }

    public static UeditorResponse responseWithSuccess(String url, String title, String original, String type, String size) {
        return new UeditorResponse(UeditorEnum.SUCCESS.msg, url, title, original, type, size);
    }

    public static UeditorResponse responseWithFailure() {
        return new UeditorResponse(UeditorEnum.ERROR.msg, "", "", "", "", "");
    }

    public enum UeditorEnum {
        SUCCESS("SUCCESS"), ERROR("ERROR");
        private final String msg;

        UeditorEnum(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}