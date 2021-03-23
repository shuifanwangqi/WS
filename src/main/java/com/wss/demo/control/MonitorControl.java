package com.wss.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wss.demo.service.LoginService;
import com.wss.demo.service.MonitorService;
import com.wss.demo.util.Result;
import com.wss.demo.vo.MonitorVo;

@RestController
public class MonitorControl {
     @Autowired
     MonitorService monitorService;
     @RequestMapping("/monitor")
     public Result getMonitorVo(){
         MonitorVo monitorVo=monitorService.findAddress();
         return new Result().success(monitorVo);
     }
}
