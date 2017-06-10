package Wxzt.servlet.table.web.dictionary;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/29.
 */
public class Delete_Dict {
    public String updata(HttpServletRequest request) throws IOException {
        String ID = request.getParameter("ID");//ID
        System.out.println("ID================="+ID+"--------------------");
        return "delete from px_CRM_Dict where ID="+ID;
    }
}
