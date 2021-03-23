package com.wss.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Value("${yingshi.appKey}")
    private String appKey;

    @Value("${yingshi.appSecret}")
    private String appSecret;

    public String getaTUrl() {
        return aTUrl;
    }

    public void setaTUrl(String aTUrl) {
        this.aTUrl = aTUrl;
    }

    @Value("${yingshi.accessTokenUrl}")
    private  String aTUrl;

    public String getAddressUrl() {
        return addressUrl;
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }

    @Value("${yingshi.addressUrl}")
    private String addressUrl;

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    @Value("${yingshi.deviceSerial}")
    private String deviceSerial;
}
