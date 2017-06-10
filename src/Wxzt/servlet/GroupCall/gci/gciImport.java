package Wxzt.servlet.GroupCall.gci;

import Wxzt.servlet.Import.DataImport;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016-10-18.
 */
public class gciImport extends DataImport {
    private static final long serialVersionUID = 1L;
    /*导入数据库*/
    public void setInsert(String fileName, PrintWriter out,
                          String workerNum,String importId,String CustNature,String custOwner) throws IOException, SQLException {
        gci d = new gci();//excel文件导入数据库
        d.db(fileName,out,importId);
    }
}
