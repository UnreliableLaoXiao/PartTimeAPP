package com.example.updateapp.entity;

/**
 * Created by Auser on 2018/4/11.
 */

public class Version {

    private String version;
    private String descript;
    private String url;
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getDescript() {
        return descript;
    }
    public void setDescript(String descript) {
        this.descript = descript;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Version(String version, String descript, String url) {
        super();
        this.version = version;
        this.descript = descript;
        this.url = url;
    }

}
