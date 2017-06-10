package Wxzt.servlet.bean.background;

import java.util.ArrayList;

/**
* Created by Pengxi on 2015/9/1.
*/
public class User {
    private Integer count;//数据总量
    private Integer countPage;//总页数
    private Integer currentPage;//当前页数
    private ArrayList<String[]> DataList;//数据库中的数据
    private String dataType;//查询结果是否有数据

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCountPage() {
        return countPage;
    }
    public void setCountPage(Integer countPage) {
        this.countPage = countPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<String[]> getDataList() {
        return DataList;
    }
    public void setDataList(ArrayList<String[]> DataList) {
        this.DataList = DataList;
    }

    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
