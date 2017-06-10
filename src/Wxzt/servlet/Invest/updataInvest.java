package Wxzt.servlet.Invest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-11.
 */
public class updataInvest extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataCust");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getInvSQL(request,action));
    }

    /*action:0为删除，1位更新，2为添加，3为回收*/
    public boolean getInvSQL(HttpServletRequest request,String action){
        investSql sql = new investSql();
        if(Objects.equals(action, "0")){
            return sql.investDel(request);
        }
        return false;
    }
}