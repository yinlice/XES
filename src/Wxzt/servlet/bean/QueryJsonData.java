package Wxzt.servlet.bean;

import java.util.ArrayList;

public class QueryJsonData {
    private ArrayList<ArrayList> DataList;//数据库中的数据
    private ArrayList<ArrayList> DataList_T;//数据库中第二张表的数据
    private ArrayList DataList_O;//数据库中第二张表的数据
    private String dataType;//查询结果是否有数据
    private Object config;


    public ArrayList<ArrayList> getDataList() {
        return DataList;
    }
    public void setDataList(ArrayList<ArrayList> DataList) {
        this.DataList = DataList;
    }

    public ArrayList<ArrayList> getDataList_T() {
        return DataList_T;
    }
    public void setDataList_T(ArrayList<ArrayList> DataList_T) {
        this.DataList_T = DataList_T;
    }

    public ArrayList getDataList_O() {
        return DataList_O;
    }
    public void setDataList_O(ArrayList DataList_O) {
        this.DataList_O = DataList_O;
    }

    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getConfig() {
        return config;
    }
    public void setConfig(Object config) {
        this.config = config;
    }
}
