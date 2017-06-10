package Wxzt.servlet.table.web.knowledge;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Insert_KB {
    public String updata(HttpServletRequest request) throws IOException {
        String Category = request.getParameter("Category");//工单编号
        String Title = request.getParameter("Title");//工单主题
        String Content = request.getParameter("Content");//备注
        System.out.println(Content);
        String sql = "INSERT  into px_CRM_KnowledgeBase (KB_Category,KB_Title,KB_Content)" +
                " values ('"+Category+"','"+Title+"','"+Content+"')";
        return sql;
    }
}
