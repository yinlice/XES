package Wxzt.servlet.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/30.
 */
public class HomePage {
    private ArrayList<ArrayList> DataList_A;//个人战绩
    private ArrayList<ArrayList> DataList_B_1;//英雄排行榜-接听量
    private ArrayList<ArrayList> DataList_B_2;//英雄排行榜-在线时长
    private ArrayList<ArrayList> DataList_B_3;//英雄排行榜-满意度
    private String dataType;//查询结果是否有数据
    private String UserNum;//设置table_A的工号


    public ArrayList<ArrayList> getDataList_A() {
        return DataList_A;
    }
    public void setDataList_A(ArrayList<ArrayList> DataList_A) {
        this.DataList_A = DataList_A;
    }

    public ArrayList<ArrayList> getDataList_B_1() {
        return DataList_B_1;
    }
    public void setDataList_B_1(ArrayList<ArrayList> DataList_B_1) {
        this.DataList_B_1 = DataList_B_1;
    }

    public ArrayList<ArrayList> getDataList_B_2() {
        return DataList_B_2;
    }
    public void setDataList_B_2(ArrayList<ArrayList> DataList_B_2) {
        this.DataList_B_2 = DataList_B_2;
    }

    public ArrayList<ArrayList> getDataList_B_3() {
        return DataList_B_3;
    }
    public void setDataList_B_3(ArrayList<ArrayList> DataList_B_3) {
        this.DataList_B_3 = DataList_B_3;
    }

    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUserNum() {
        return UserNum;
    }
    public void setUserNum(String UserNum) {
        this.UserNum = UserNum;
    }
}
