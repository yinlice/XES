package Wxzt.servlet.Import;

import Wxzt.tool.JDBC;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/23.
 */
public class DataDB {
    private static Logger log = Logger.getLogger(DataDB.class);
    public static void dataDB(String fileURL, PrintWriter out,
                              String workerNum,String importId,String CustNature,String custOwner){
        try{
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(fileURL)));// 创建工作薄
            HSSFSheet sheet = workbook.getSheetAt(0);// 得到工作表
            HSSFRow row;// 对应excel的行
            HSSFCell cell;// 对应excel的列

            int totalRow = sheet.getLastRowNum();//得到excel的总记录条数
            System.out.println(totalRow);

            ArrayList<String> cellRow = new ArrayList<String>();
            JDBC jdbc = new JDBC();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
            SimpleDateFormat format_1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            SimpleDateFormat format_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//用于H5的时间
            String newTime = format_2.format(new Date());//用于h5的现在时间
            String sql = "";
            int m = 0;//成功导入数据数量
            ArrayList<Integer> falseInsert = new ArrayList<Integer>();
            for (int i = 1;i<totalRow+1;i++) {//添加数据
                row = sheet.getRow(i);//获取每一行的内容（去除表头）
                cellRow.clear();
                for(int j=0;j<row.getLastCellNum();j++) { //把一行里的每一个字段遍历出来
                    cell = row.getCell(j);//创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    cellRow.set(j,cell.getStringCellValue());
                    cellRow.add(cell.getStringCellValue());
                    System.out.println(cell.getStringCellValue());
                }
                sql = "INSERT  into px_CRM_Cust (CustNum,dtGive,dtCreate,dtUpdate,CustName,Mobile,Addr,WorkNumOwner,ImportID) " +
                        "values ('1000-"+format.format(new Date())+"-"+i+"','"+newTime+"','"+newTime+"','"+newTime+"','"+ cellRow.get(0) +"','"+cellRow.get(1)+"','"+cellRow.get(2)+"','"+custOwner+"','"+importId+"')";
                System.out.println(sql);
                boolean importBool = jdbc.executeUpdate(sql);
                if(importBool) {
                    m++;
                }else {
                    falseInsert.add(i);
                }
            }
            //添加导入情况
            if(m!=0){
                String sqlIn = "insert into px_CRM_ImportStation (ImportID,CreatTime,CreatWorker,custOwner,totalNum,isShow) values " +
                        "('"+importId+"','"+format_1.format(new Date())+"','"+workerNum+"','"+custOwner+"','"+m+"','0')";
                System.out.println("导入情况==" + sqlIn);
                jdbc.executeUpdate(sqlIn);
            }
            out.println("总共导入" +totalRow+"条数据\n"+
                    "导入成功"+m+"条数据！\n"+
                    "导入失败"+falseInsert.size() +"条数据！\n"
                    +((falseInsert.size()>0)?"错误数据为第"+falseInsert.toString()+"条":""));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
