package Wxzt.tool;

import Wxzt.servlet.Common.toExcel.excelBean;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/1/8.
 */
public class SimpleExcelWrite {
    public void createExcel(OutputStream os,excelBean isExcelBean) throws WriteException,IOException {
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        int lineCount = isExcelBean.getCount();//数据的行数
        int columnCount = isExcelBean.getExcelData().length;//列数
        System.out.println("lineCount="+lineCount+"-----11--columnCount"+columnCount);
        for(int i=0;i<lineCount+1;i++){//数据的行数+表头的行数
            for(int j=0;j<columnCount;j++){
                String excelData;//每行每列的数据
                if(i==0){
                    excelData = isExcelBean.getExcelData()[j];//生成表头
                }else {
                    excelData = (String) isExcelBean.getDataList().get(i-1).get(j);//生成数据库的数据
                }
                Label data = new Label(j,i,excelData==null?"":excelData);
                sheet.addCell(data);
            }
        }
        /*把创建的内容写入到输出流中，并关闭输出流*/
        workbook.write();
        workbook.close();
        os.close();
    }
}
