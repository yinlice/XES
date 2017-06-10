package Wxzt.servlet.QueryReport;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/4.
 */
public class ReportBean {
    private String name;
    private ArrayList data;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
