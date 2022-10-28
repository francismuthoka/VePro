package com.example.digiseedbed;

public class Helper {
    String timetxt,code_donetxt,name,activity_donetxt,Reason_donetxt,time;

    public Helper() {
    }

    public String getTimetxt() {
        return timetxt;
    }

    public void setTimetxt(String timetxt) {
        this.timetxt = timetxt;
    }

    public String getCode_donetxt() {
        return code_donetxt;
    }

    public void setCode_donetxt(String code_donetxt) {
        this.code_donetxt = code_donetxt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity_donetxt() {
        return activity_donetxt;
    }

    public void setActivity_donetxt(String activity_donetxt) {
        this.activity_donetxt = activity_donetxt;
    }

    public String getReason_donetxt() {
        return Reason_donetxt;
    }

    public void setReason_donetxt(String reason_donetxt) {
        Reason_donetxt = reason_donetxt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Helper(String timetxt, String code_donetxt, String name, String activity_donetxt, String reason_donetxt, String time) {
        this.timetxt = timetxt;
        this.code_donetxt = code_donetxt;
        this.name = name;
        this.activity_donetxt = activity_donetxt;
        Reason_donetxt = reason_donetxt;
        this.time = time;
    }
}
