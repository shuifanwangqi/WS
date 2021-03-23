package com.wss.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wss.demo.service.MonitorService;
import com.wss.demo.util.Connection;
import com.wss.demo.util.Properties;
import com.wss.demo.vo.MonitorVo;

import java.util.concurrent.ConcurrentMap;

@Component
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    Properties properties;
    @Override
    public MonitorVo findAddress() {
         String appKey=properties.getAppKey();
         String appSecret=properties.getAppSecret();
         String accessTokenUrl=properties.getaTUrl()+"?appKey="+appKey+"&appSecret="+appSecret;
         JSONObject accessTokenJson= Connection.getJsonData(accessTokenUrl);
         JSONObject data=(JSONObject)accessTokenJson.get("data");
         String accessToken=data.getString("accessToken");
         String AddressUrl=properties.getAddressUrl()+"?accessToken="+accessToken+"&deviceSerial="+properties.getDeviceSerial();
         JSONObject AddressJsonObject=Connection.getJsonData(AddressUrl);
         JSONObject AddressData=(JSONObject) AddressJsonObject.get("data");
         MonitorVo monitorVo=new MonitorVo();
         monitorVo.setHdAddress(AddressData.getString("hdAddress"));
         monitorVo.setLiveAddress(AddressData.getString("liveAddress"));
         return monitorVo;
    }
}
