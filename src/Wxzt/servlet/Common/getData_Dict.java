package Wxzt.servlet.Common;

import Wxzt.tool.JDBC;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12.
 */
public class getData_Dict {
    public ArrayList<ArrayList> getDict(String DictType) throws IOException {
        String sql = "select * from V_Dict where DictType in ("+DictType+")";//查询语句
        ResultSet os;//记录总数据数量的ResultSet
        JDBC jdbc = new JDBC();
        ArrayList<ArrayList> dict = new ArrayList<ArrayList>();
        System.out.println("dictSQL=="+sql);
        try {
            os = jdbc.executeQuery(sql);
            try {
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("parentID"));//父级菜单ID-0
                    data.add(os.getString("DictType"));//菜单类别-1
                    data.add(os.getString("DictCode"));//菜单value-2
                    data.add(os.getString("DictMean"));//菜单页面显示值-3
                    dict.add(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return dict;
    }
}
