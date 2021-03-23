package com.wss.demo.vo;

public class AlarmVo {
    private Integer id;//id
    private String location;//图片地址，根据这个得到图片
    private Boolean isPain;//是否疼痛
    private Boolean isFall;//是否摔倒
    private String roomNumber;//房间号

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    public Boolean getPain() {
        return isPain;
    }

    public void setPain(Boolean pain) {
        isPain = pain;
    }

    public Boolean getFall() {
        return isFall;
    }

    public void setFall(Boolean fall) {
        isFall = fall;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }





}
