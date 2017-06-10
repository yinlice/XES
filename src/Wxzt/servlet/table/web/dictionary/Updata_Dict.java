package Wxzt.servlet.table.web.dictionary;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/29.
 */
public class Updata_Dict {
    public String updata(HttpServletRequest request) throws IOException {
        String ID = request.getParameter("ID");//ID
        String DictName = request.getParameter("DictName");//字典类别
        String DictCode = request.getParameter("DictCode");//字典value
        String DictType = request.getParameter("DictType");//字典编号
        String DictMean = request.getParameter("DictMean");//字典显示值
        String Note = request.getParameter("Note");//备注
        System.out.println("_______________"+ID);
        System.out.println("_________DictName______"+DictName);
        System.out.println("_______DictCode________"+DictCode);
        System.out.println("________DictMean_______"+DictMean);
        System.out.println("_________DictType______"+DictType);
        System.out.println("_________Note______"+Note);
        return "update px_CRM_Dict set DictName = '"+DictName+"',DictCode = '"+DictCode+"',DictType = '"+DictType+"',DictMean = '"+DictMean+"',Note = '"+Note+"' WHERE ID="+ID;
    }
}
