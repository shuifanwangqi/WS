package com.wss.demo.dao;

import com.wss.demo.dto.AlarmTempDto;
import org.apache.ibatis.annotations.Mapper;
import com.wss.demo.dto.AlarmDto;
import com.wss.demo.vo.AlarmVo;
import com.wss.demo.vo.RoomVo;

import java.util.List;
@Mapper
public interface AlarmDao {
    List<AlarmVo>  getAlarm(AlarmDto alarmDto);
    List<RoomVo> getRoom();
    void deleteAlarm(Integer id);
    int getTotal(AlarmTempDto alarmTempDto);
}
