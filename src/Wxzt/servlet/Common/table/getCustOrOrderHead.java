package Wxzt.servlet.Common.table;

import Wxzt.tool.IniReader;
import Wxzt.tool.JDBC;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016-9-6.
 */
public class getCustOrOrderHead {
    public String[] getHeadList(String headName,String headType) throws IOException {
        File f = new File(JDBC.class.getClassLoader().getResource("/").getPath());
        String urlIni = f+"\\conf\\sysconf.ini";
        IniReader reader = new IniReader(urlIni);
        String head = reader.getValue(headName,headType);
        return head.split(",");
    }
}
