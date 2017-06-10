package Wxzt.servlet.bean.Common;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-5-8.
 */
public class queryJson {
    protected Integer count;//数据总量
    protected Integer countPage;//总页数
    protected Integer currentPage;//当前页数
    protected String sqlTable;//查询语句
    protected ArrayList<Object> dataList;//数据库中的数据
    protected ArrayList<Object> tableHead;

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

    public ArrayList<Object> getDataList() {
        return dataList;
    }
    public void setDataList(ArrayList<Object> dataList) {
        this.dataList = dataList;
    }

    public void setSqlTable(String sqlTable){
        this.sqlTable = sqlTable;
    }
    public String getSqlTable(){
        return this.sqlTable;
    }

    public void setTableHead(ArrayList<Object> tableHead){
        this.tableHead = tableHead;
    }
    public ArrayList<Object> getTableHead(){
        return this.tableHead;
    }
}
