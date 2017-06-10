package Wxzt.tool;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/2.
 */
public class upData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(upData.class);
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:/55.xls")));// 创建工作薄
            HSSFSheet sheet = workbook.getSheetAt(0);// 得到工作表
            HSSFRow row;// 对应excel的行
            HSSFCell cell;// 对应excel的列

            int totalRow = sheet.getLastRowNum();//得到excel的总记录条数
            System.out.println(totalRow);
            String worknum = "";//编号
            String workname = "";//名称

            for (int i = 1;i<totalRow;i++) {
//                dddWork excel = new dddWork();
                row = sheet.getRow(i);
                if(row.getCell(0)!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                }
                //把一行里的每一个字段遍历出来
                for(int j=0;j<row.getLastCellNum();j++) {
//创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
                    cell = row.getCell(j);
//在这里我们就可以做很多自己想做的操作了，比如往数据库中添加数据等
                    System.out.println(cell.getRichStringCellValue());
                }
//                cell = row.getCell(0);
//                if(cell!=null)
//                    worknum = String.valueOf(cell.getRichStringCellValue());
//                excel.setWorknum(worknum);
//
//                cell = row.getCell(1);
//                if(cell!=null)
//                    workname = String.valueOf(cell.getRichStringCellValue());
//                excel.setWorkname(workname);
//                System.out.println(worknum+"----"+workname+"----");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
