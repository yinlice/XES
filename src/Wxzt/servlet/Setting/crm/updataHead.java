package Wxzt.servlet.Setting.crm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-7-18.
 */
public class updataHead extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataHead");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCustSQL(request,action));
    }

    /*action:0为删除，1位更新，2为添加*/
    public boolean getCustSQL(HttpServletRequest request,String action){
        queryHeadSql sql = new queryHeadSql();
        if(Objects.equals(action, "0")){
            return sql.delHead(request);
        }else if(Objects.equals(action, "1")) {
            return sql.updateHead(request);
        }else if(Objects.equals(action, "2")){
            return sql.insertHead(request);
        }
        return false;
    }
}
