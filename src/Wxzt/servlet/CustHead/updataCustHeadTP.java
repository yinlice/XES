package Wxzt.servlet.CustHead;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-11-10.
 */
public class updataCustHeadTP extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataCustHeadTP");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCustSQL(request,action));
    }

    /*action:0为删除，1为更新，2为添加*/
    public boolean getCustSQL(HttpServletRequest request,String action){
        custHeadTPSql chtp = new custHeadTPSql();
        if(Objects.equals(action, "0")){/*删除客户*/
            return chtp.custHeadTPDel(request);
        }else if(Objects.equals(action, "1")) {/*更新客户*/
            return chtp.updateCustHeadTP(request);
        }else if(Objects.equals(action, "2")){/*添加客户*/
            return chtp.insertCustHeadTP(request);
        }
        return false;
    }
}
