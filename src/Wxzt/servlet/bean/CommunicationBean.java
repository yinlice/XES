package Wxzt.servlet.bean;

import java.util.ArrayList;

/**
* Created by Pengxi on 2015/8/25.
*/
public class CommunicationBean {
    private String FirstTime;//新建时间
    private String Record;//沟通记录
    private ArrayList<String[]> DataList;//数据库中的数据

    public String getFirstTime() {
        return FirstTime;
    }
    public void setFirstTime(String FirstTime) {
        this.FirstTime = FirstTime;
    }

    public String getRecord() {
        return Record;
    }
    public void setRecord(String countPage) {
        this.Record = Record;
    }


    public ArrayList<String[]> getDataList() {
        return DataList;
    }
    public void setDataList(ArrayList<String[]> DataList) {
        this.DataList = DataList;
    }
}
