package com.wss.demo.control;

import com.wss.demo.dto.AlarmTempDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wss.demo.dto.AlarmDto;
import com.wss.demo.service.AlarmService;
import com.wss.demo.util.Result;
import com.wss.demo.vo.AlarmVo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/alarm")
public class AlarmControl {

    @Autowired
    AlarmService alarmService;

    @RequestMapping(value = "/show" , method = RequestMethod.POST )
    public Result  showAlarm(@RequestBody AlarmDto alarmDto){
        Result result=new Result();
        HashMap<String,Object> map=new HashMap<>();
        map.put("pageIndex",alarmDto.getPageIndex());
        map.put("pageSize",alarmDto.getPageSize());
        alarmDto.setPageIndex((alarmDto.getPageIndex()-1)*alarmDto.getPageSize());
        //alarmDto.setPageSize(alarmDto.getPageSize()*alarmDto.getPageIndex());
        List<AlarmVo> list=alarmService.getAlarm(alarmDto);
        AlarmTempDto alarmTempDto=new AlarmTempDto();
        alarmTempDto.setEndDate(alarmDto.getEndDate());
        alarmTempDto.setStartDate(alarmDto.getStartDate());
        alarmTempDto.setRoomNumber(alarmDto.getRoomNumber());
        int total=alarmService.getTotal(alarmTempDto);
        map.put("alarms",list);
        map.put("total",total);
        return result.success(map);
    }

    @RequestMapping("/getRoom")
    public Result getRoom(){
        Result result=new Result();
        return result.success(alarmService.getRoom());
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result deleteAlarm(@RequestParam Integer id){
        Result result=new Result();
        try{
            alarmService.deleteAlarm(id);
        }catch (Exception e){
            return result.fail("删除失败");
        }
        return result.success(null);
    }
}
