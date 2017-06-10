package Wxzt.tool;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ReadExcel {

    public static void readExcel(String pathname, PrintWriter out) {
        try {
            //打开文件
            Workbook book = Workbook.getWorkbook(new File(pathname)) ;
            //取得第一个sheet
            Sheet sheet = book.getSheet(0);
            //取得行数
            int rows = sheet.getRows();
            for(int i = 0; i < rows; i++) {
                Cell[] cell = sheet.getRow(i);
                for(int j=0; j<cell.length; j++) {
                    //getCell(列，行)
//                    out.print(sheet.getCell(j, i).getContents());
//                    out.print(" ");
                }
                out.println("<br/>");
            }
            //关闭文件
            book.close();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}