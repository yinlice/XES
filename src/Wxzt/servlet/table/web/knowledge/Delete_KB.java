package Wxzt.servlet.table.web.knowledge;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Delete_KB {
    public String updata(HttpServletRequest request) throws IOException {
        String sql = "delete from px_CRM_KnowledgeBase where ID='"+request.getParameter("ID")+"'";
        System.out.println("删除知识库语句："+sql);
        return sql;
    }
}
