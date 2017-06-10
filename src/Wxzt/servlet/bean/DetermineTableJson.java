package Wxzt.servlet.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/25.
 */
public class DetermineTableJson {
    private int total;//查询结果是否有数据
    private ArrayList<Object> rows;//数据库中的数据

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Object> getRows() {
        return rows;
    }
    public void setRows(ArrayList<Object> rows) {
        this.rows = rows;
    }
}
