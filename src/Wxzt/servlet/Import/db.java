package Wxzt.servlet.Import;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Cust.custSql;
import Wxzt.servlet.Cust.repeatCustBean;
import Wxzt.tool.JDBC;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-8-17.
 */
public class db extends Query{
    private static Logger log = Logger.getLogger(DataDB.class);
    public String repeatTable = null;
    public void db(String fileURL, PrintWriter out,
                          String workerNum,String importId,String CustNature,String custOwner) throws IOException,SQLException {
        try {
            String outString = null;
            setRepeatTable();
            System.out.println("已成功生成备份表");

            ArrayList<ArrayList<String>> telList = insertCustToBackup(fileURL, workerNum, importId, CustNature, custOwner);
            int telListLength = telList.size();
            ArrayList<String> telList_T = new ArrayList<>();/*成功导入备份数据库电话*/
            ArrayList<String> telList_F = new ArrayList<>();/*excel中重复的电话*/
            if(telListLength==0){
                out.println("未导入数据");
                return;
            }else if(telListLength==1){
                telList_T = telList.get(0);
            }else {
                telList_T = telList.get(0);
                telList_F = telList.get(1);
            }
            System.out.println("已成功导入备份表");

            custSql cust = new custSql();
            String sqlToRepaet = "select mobile from "+this.repeatTable;
            ArrayList<Object> repeatCustBeanArrayList =  cust.queryRepeatCustForDB(sqlToRepaet);
            ArrayList<String> backUpTel = new ArrayList<>();/*成功导入备份数据库，并且相对于已有客户的重复电话*/
            String mobile = "",telnum = "";
            for(int r = 0;r<repeatCustBeanArrayList.size();r++){
                repeatCustBean rep = (repeatCustBean) repeatCustBeanArrayList.get(r);
                mobile = rep.mobile;
                telnum = rep.telnum;
                if(mobile!=null&& !Objects.equals(mobile, "")){
                    backUpTel.add("'"+mobile+"'");
                }
                if(telnum!=null&& !Objects.equals(telnum, "")){
                    backUpTel.add("'"+telnum+"'");
                }
            }
            System.out.println("已成功查询重复数据");

            int insertT = telList_T.size(),insertF = telList_F.size(),backUp = backUpTel.size();
            int trueInsert = insertT - backUp;
            if(trueInsert>0){
                insertStation(importId,workerNum,custOwner,trueInsert);
            }

            boolean inset = insertTelToCust(backUpTel);
            if(inset){
                outString = "导入成功"+ trueInsert +"条数据！\n"+
                        "Excel中总共" +(insertT+insertF)+"条数据,其中:\n"+
                        "Excel重复"+insertF +"条数据:\n"+
                        ((insertF>0)?("["+join(telList_T,",")+"]\n"):"[]\n")+
                        "数据库重复"+backUp +"条数据:\n"+
                        ((backUp>0)?"["+join(backUpTel,",")+"]":"[]");
                out.println(outString);
            }else {
                out.println("导入excel到客户数据出错");
            }
        }catch (Exception e){
            out.println("导入客户数据出错");
        }finally {
            delRepeatTable();
        }

    }

    /*****生成备份库*****/
    public void setRepeatTable() throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        this.repeatTable = "custContact"+format.format(new Date());
        String sql = "create table "+this.repeatTable+" (custname varchar(30),mobile varchar(30),addr varchar(50)," +
                "ImportID varchar(50),CustNum varchar(50),dtGive varchar(20),dtCreate datetime,dtUpdate datetime," +
                "WorknumOwner varchar(20),WorknumCreate varchar(20));";
        JDBC jdbc = new JDBC();
        jdbc.executeUpdate(sql);
        jdbc.closeConnection();
    }

    /*****删除备份库*****/
    public void delRepeatTable() {
        String sql = "drop table "+this.repeatTable;
        try {
            JDBC jdbc = new JDBC();
            jdbc.executeUpdate(sql);
            jdbc.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*****生成导入数据到备份库*****/
    public ArrayList<ArrayList<String>> insertCustToBackup(String fileURL,
                                                String workerNum,String importId,String other,String custOwner) throws IOException,SQLException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(fileURL)));// 创建工作薄
        HSSFSheet sheet = workbook.getSheetAt(0);// excel数据
        HSSFRow row;// excel的行
        HSSFCell cell;// excel的列
        int totalRow = sheet.getLastRowNum();//excel的总行数
        ArrayList<String> cellRow = new ArrayList<String>();

        JDBC jdbc = new JDBC();
//        jdbc.conBool = false;
        Connection con = jdbc.getCon();

        String sql = "insert into "+this.repeatTable+" (custname,mobile,addr,custnum,ImportID,dtGive,dtCreate,dtUpdate," +
                "WorknumOwner,WorknumCreate) values (?,?,?,?,?,?,?,?,?,?)";
        String tel = "", custname="", addr = "";
        ArrayList<String> insertTel = new ArrayList<>();/*导入到备份数据库的电话数组*/
        ArrayList<String> insertTelFales = new ArrayList<>();/*重复的电话数组*/
        ArrayList<ArrayList<String>> telList = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
        SimpleDateFormat format_1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String newTime = format_1.format(new Date());

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
                }else {
                    /*excel中电话的排重*/
                    String telnum = "'"+tel+"'";
                    if(insertTel.contains(telnum)){
                        insertTelFales.add(telnum);
                        continue;
                    }else {
                        insertTel.add(telnum);
                    }
                }
                /*设置用户名称的默认名称*/
                int length = cellRow.size();
                if(length>=2&&cellRow.get(1)!=null){
                    custname = cellRow.get(1);
                }else {
                    custname = "";
                }
                /*设置空地址的默认地址*/
                if(length>=3&&cellRow.get(2)!=null){
                    addr = cellRow.get(2);
                }else {
                    addr = "";
                }
                prest.setString(1, custname);
                prest.setString(2, tel);
                prest.setString(3, addr);
                prest.setString(4, "1000-"+format.format(new Date())+i);
                prest.setString(5, importId);
                prest.setString(6, newTime);
                prest.setString(7, newTime);
                prest.setString(8, newTime);
                prest.setString(9, custOwner);
                prest.setString(10, workerNum);
                prest.addBatch();
            }
            prest.executeBatch();
            jdbc.closeConnection();
            telList.add(insertTel);
            telList.add(insertTelFales);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return telList;
    }
    /*****将不重复的电话添加到客户数据库*****/
    public boolean insertTelToCust(ArrayList<String> telList) throws IOException , SQLException{
        String sql,str = "";
        if(telList.size()>0){
            String telListString = join(telList,",");
            str = " where mobile not in ("+telListString+")";
        }
        sql = "insert into px_crm_cust(custname,mobile,addr,custnum,ImportID,dtGive,dtCreate,dtUpdate,WorknumOwner,WorknumCreate) " +
                "select custname,mobile,addr,custnum,ImportID,dtGive,dtCreate,dtUpdate,WorknumOwner,WorknumCreate from "+this.repeatTable+" "+str;
        JDBC jdbc = new JDBC();
        return jdbc.executeUpdate(sql);
    }
    /*****添加导入情况*****/
    public boolean insertStation(String importId,String workerNum,String custOwner,int m) throws IOException{
        boolean insertBool = false;
        try {
            JDBC jdbc = new JDBC();
            SimpleDateFormat format_1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String newTime = format_1.format(new Date());

            String sqlIn = "insert into px_CRM_ImportStation (ImportID,CreatTime,CreatWorker,custOwner,totalNum,isShow) values " +
                    "('"+importId+"','"+newTime+"','"+workerNum+"','"+custOwner+"','"+m+"','0')";
            System.out.println("导入情况语句：" + sqlIn);
            insertBool = jdbc.executeUpdate(sqlIn);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return insertBool;
        }

    }
}
