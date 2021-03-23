package com.wss.demo.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.wss.demo.dto.AlarmTempDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wss.demo.dao.AlarmDao;
import com.wss.demo.dto.AlarmDto;
import com.wss.demo.service.AlarmService;
import com.wss.demo.vo.AlarmVo;
import com.wss.demo.vo.RoomVo;

import java.util.List;
@Component
public class AlarmServiceImpl implements AlarmService {
    @Autowired
    AlarmDao alarmDao;

    @Override
    public List<AlarmVo> getAlarm(AlarmDto alarmDto) {

        return alarmDao.getAlarm(alarmDto);
    }

    public List<RoomVo> getRoom(){
        return alarmDao.getRoom();
    }

    @Override
    public void deleteAlarm(Integer id) {
          alarmDao.deleteAlarm(id);
    }

    @Override
    public Integer getTotal(AlarmTempDto alarmTempDto) {
        return  alarmDao.getTotal(alarmTempDto);
    }
}
