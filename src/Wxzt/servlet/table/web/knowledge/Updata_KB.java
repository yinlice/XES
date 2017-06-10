package Wxzt.servlet.table.web.knowledge;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Updata_KB {
    public String updata(HttpServletRequest request) throws IOException {
        String Category = request.getParameter("Category");//类别
        String Title = request.getParameter("Title");//标题
        String Content = request.getParameter("Content");//内容
        String ID = request.getParameter("ID");//ID
        String sql = "update px_CRM_KnowledgeBase set KB_Category = '"+Category+"',KB_Title = '"+Title+"',KB_Content = '"+Content+"'\n" +
                "where ID = '"+ID+"'";
        System.out.println("Updata_KB=="+sql);
        return sql;
    }
}
