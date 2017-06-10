package Wxzt.servlet.table.web.dictionary;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/29.
 */
public class Insert_Dict {
    public String updata(HttpServletRequest request) throws IOException {
        String DictName = request.getParameter("DictName");//字典类别
        String DictCode = request.getParameter("DictCode");//字典value
        String DictType = request.getParameter("DictType");//字典编号
        String DictMean = request.getParameter("DictMean");//字典显示值
        String Note = request.getParameter("Note");//备注
        //String dictID = request.getParameter("dictID");
        String sql = "INSERT into px_CRM_Dict (DictName,DictCode,DictType,DictMean,Note)" +
                " values ('"+DictName+"','"+DictCode+"','"+DictType+"','"+DictMean+"','"+Note+"')";
        System.out.println("工单添加："+sql);
        return sql;
    }
}
