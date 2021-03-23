package com.wss.demo.service;

import com.wss.demo.dto.AlarmDto;
import com.wss.demo.dto.AlarmTempDto;
import com.wss.demo.vo.AlarmVo;
import com.wss.demo.vo.RoomVo;

import java.util.List;

public interface AlarmService {
    List<AlarmVo> getAlarm(AlarmDto alarmDto);
    List<RoomVo> getRoom();
    void deleteAlarm(Integer id);
    Integer getTotal(AlarmTempDto alarmTempDto);
}
