package Wxzt.servlet.Common;

import Wxzt.tool.JdbcForCti;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016-7-26.
 */
public class QueryForCti extends Query {
    /*获取单个数据*/
    public String sqlOneData(String sql) {
        try {
            JdbcForCti jdbc = new JdbcForCti();
            ResultSet osCount = jdbc.executeQuery(sql);
            if (osCount.next()) {
                return osCount.getString(1);
            }else {
                return "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*获取多个数据*/
    public ArrayList<Object> sqlListData(String sql,String[] dataName,String tableType){
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            JdbcForCti jdbc = new JdbcForCti();
            ResultSet os = jdbc.executeQuery(sql);
            while (os.next()) {
                Object data = this.getTableObject(tableType);
                for(int i = 0;i<dataName.length;i++){
//                    String oneData = os.getString(dataName[i])==null?"":os.getString(dataName[i]);
                    BeanUtils.setProperty(data, dataName[i], os.getString(dataName[i]));
                }
                list.add(data);
            }
            jdbc.closeConnection();
            return list;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*语句的更新、添加、删除等操作*/
    public boolean updataSql(String sql){
        try {
            JdbcForCti jdbc = new JdbcForCti();
            boolean s = jdbc.executeUpdate(sql);
            jdbc.closeConnection();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*获取CTI查询分类*/
    public String getCompanyCode(){
        /*获取录音在CTI中分类，查询条件*/
        return getConfig("SQLServerForCti","CtiName");
    }
}
