package com.wxzt.pojo;

public class WorkerDayReport {
    private Integer id;

    private String worknum;

    private String dtupdate;

    private Integer call;

    private Integer nocall;

    private Integer total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorknum() {
        return worknum;
    }

    public void setWorknum(String worknum) {
        this.worknum = worknum == null ? null : worknum.trim();
    }

    public String getDtupdate() {
        return dtupdate;
    }

    public void setDtupdate(String dtupdate) {
        this.dtupdate = dtupdate == null ? null : dtupdate.trim();
    }

    public Integer getCall() {
        return call;
    }

    public void setCall(Integer call) {
        this.call = call;
    }

    public Integer getNocall() {
        return nocall;
    }

    public void setNocall(Integer nocall) {
        this.nocall = nocall;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}