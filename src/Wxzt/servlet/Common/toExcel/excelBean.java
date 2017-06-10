package Wxzt.servlet.Common.toExcel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/8.
 */
public class excelBean {
    private ArrayList<ArrayList> DataList;//数据库中的数据
    private String[] ExcelData;//Excel表头数据
    private Integer count;//数据总量

    public ArrayList<ArrayList> getDataList() {
        return DataList;
    }
    public void setDataList(ArrayList<ArrayList> DataList) {
        this.DataList = DataList;
    }

    public String[] getExcelData() {
        return ExcelData;
    }
    public void setExcelData(String[] ExcelData) {
        this.ExcelData = ExcelData;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}
