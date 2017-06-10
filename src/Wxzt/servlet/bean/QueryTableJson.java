package Wxzt.servlet.bean;

import java.util.ArrayList;

public class QueryTableJson {
    private Integer count;//数据总量
    private Integer countPage;//总页数
    private Integer currentPage;//当前页数
    private ArrayList<ArrayList> DataList;//数据库中的数据
    private ArrayList GroupDataList;//数据库中的工作组数据或者其他附带数据
    private String dataType;//查询结果是否有数据
    private String tableName;//数据表单名称
    private Object dataListObject;//数据库中的数据对象
    private ArrayList<Object> totalList = new ArrayList<>();//报表统计数据

    public void setDataListObject(Object dataListObject){
        this.dataListObject = dataListObject;
    }
    public Object getDataListObject(){
        return this.dataListObject;
    }

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

    public ArrayList<ArrayList> getDataList() {
        return DataList;
    }
    public void setDataList(ArrayList<ArrayList> DataList) {
        this.DataList = DataList;
    }

    public ArrayList getGroupDataList() {
        return GroupDataList;
    }
    public void setGroupDataList(ArrayList GroupDataList) {
        this.GroupDataList = GroupDataList;
    }

    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName1(String tableName) {
        this.tableName = tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<Object> getTotalList() {
        return totalList;
    }
    public void setTotalList(ArrayList<Object> totalList) {
        this.totalList = totalList;
    }

}
