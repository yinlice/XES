package Wxzt.servlet.Cust;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class updataCust extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataCust");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCustSQL(request,action));
    }

    /*action:0为删除，1为更新，2为添加，3为回收，4为客户页面提取，5为弹屏页面提取,6为关注操作,7为联系人删除,8为联系人更新,9为联系人添加*/
    public boolean getCustSQL(HttpServletRequest request,String action){
        custSql custsql = new custSql();
        if(Objects.equals(action, "0")){/*删除客户*/
            return custsql.custDel(request);
        }else if(Objects.equals(action, "1")) {/*更新客户*/
            return custsql.updateCust(request);
        }else if(Objects.equals(action, "2")){/*添加客户*/
            return custsql.insertCust(request);
        }else if(Objects.equals(action, "3")){/*回收客户*/
            return custsql.custRecycl(request);
        }else if(Objects.equals(action, "4")){/*客户页面提取*/
            return custsql.extractCust(request);
        }else if(Objects.equals(action, "5")){/*弹屏页面提取*/
            return custsql.extractCustOfOne(request);
        }else if(Objects.equals(action, "6")){/*关注操作*/
            return custsql.focusCust(request);
        }else if(Objects.equals(action, "7")){/*删除联系人*/
            return custsql.delCustCon(request);
        }else if(Objects.equals(action, "8")){/*更新联系人*/
            return custsql.updateCustCon(request);
        }else if(Objects.equals(action, "9")){/*添加联系人*/
            return custsql.insertCustCon(request);
        }
        return false;
    }
}
