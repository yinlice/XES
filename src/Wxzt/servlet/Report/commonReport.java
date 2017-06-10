package Wxzt.servlet.Report;

import Wxzt.tool.JdbcForCti;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016-9-12.
 */
public class commonReport {
    public ArrayList<Object> sqlListData(Object obj,String sql,String[] dataName) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            JdbcForCti jdbc = new JdbcForCti();
            ResultSet os = jdbc.executeQuery(sql);
            while (os.next()) {
                for(int i = 0;i<dataName.length;i++){
                    BeanUtils.setProperty(obj, dataName[i], os.getString(dataName[i]));
                }
                list.add(obj);
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

    public String getSql(String[] listTotal,String dataTable){
        String sqlTotal = "select ";
        for(int i = 0;i<listTotal.length;i++){
            sqlTotal += "sum("+listTotal[i]+") as "+listTotal[i]+((i==listTotal.length-1)?"":",");
        }
        sqlTotal += " from "+dataTable;
        return sqlTotal;
    }
}
