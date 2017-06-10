package Wxzt.servlet.GroupCall.gci;

import Wxzt.servlet.Common.QueryForCti;
import Wxzt.tool.JdbcForCti;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016-10-18.
 */
public class gci extends QueryForCti {
    private static Logger log = Logger.getLogger(gci.class);
    public void db(String fileURL, PrintWriter out,String importId) throws IOException,SQLException {
        try {
            insertCustToBackup(fileURL, importId);
            out.println("导入客户数据成功");
        }catch (Exception e){
            out.println("导入客户数据出错");
        }
    }
    /*****生成导入数据到数据库*****/
    public void insertCustToBackup(String fileURL,String telId) throws IOException,SQLException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(fileURL)));// 创建工作薄
        HSSFSheet sheet = workbook.getSheetAt(0);// excel数据
        HSSFRow row;// excel的行
        HSSFCell cell;// excel的列
        int totalRow = sheet.getLastRowNum();//excel的总行数
        ArrayList<String> cellRow = new ArrayList<String>();

        JdbcForCti jdbc = new JdbcForCti();
        jdbc.conBool = false;
        Connection con = jdbc.getCon();

        String sql = "insert into dddCalloutTelWait (telnum,custname,TaskId) values (?,?,"+telId+")";
        String tel = "", custname="";
        System.out.println("查询语句："+sql);
        if(!updataTelCount(telId,totalRow)) return;
        try {
            PreparedStatement prest = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for (int i = 1;i<totalRow+1;i++) {
                row = sheet.getRow(i);//行数据（去除表头）
                cellRow.clear();
                for(int j=0;j<row.getLastCellNum();j++) {//列数据
                    cell = row.getCell(j);//创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellRow.add(cell.getStringCellValue());
                }
                tel = cellRow.get(0);
                if(tel==null|| Objects.equals(tel, "")){
                    continue;
                }
                /*设置用户名称的默认名称*/
                int length = cellRow.size();
                if(length>=2&&cellRow.get(1)!=null){
                    custname = cellRow.get(1);
                }else {
                    custname = "";
                }
                System.out.println("tel=" + tel);
                prest.setString(1, tel);
                prest.setString(2, custname);
                prest.addBatch();
            }
            prest.executeBatch();
            con.commit();
            jdbc.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*更新群呼任务的数据总量*/
    public boolean updataTelCount(String telId,int count){
        String queryCount = "select telCount from dddCalloutTask where TaskId = "+telId;
        int oldCount = Integer.parseInt(sqlOneData(queryCount));
        String updataCount = "update dddCalloutTask set telCount="+(count+oldCount)+" where TaskId="+telId;
        return updataSql(updataCount);
    }
}

