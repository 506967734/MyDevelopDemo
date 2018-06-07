package com.demo.mockmodule;

import java.io.Serializable;


public class MockBean implements Serializable {
    String selected;
    String urlKey;
    String api;
    String describe;
    String fileName;
    int netWorkDelay;
    boolean closeMock=false;

    public String getFileName() {
        if("0".equals(selected))
        return fileName;
        else
            return fileName+selected;
    }

    public int getNetWorkDelay() {
        return netWorkDelay;
    }

    public void setNetWorkDelay(int netWorkDelay) {
        this.netWorkDelay = netWorkDelay;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setClose(boolean close) {
        this.closeMock = close;
    }
    public boolean  getClose() {
        return  this.closeMock;
    }
}
