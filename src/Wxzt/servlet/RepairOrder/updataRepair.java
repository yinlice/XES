package Wxzt.servlet.RepairOrder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2015/12/7.
 */
public class updataRepair extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("------------------updataRepair");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        /*获取更新语句返回值*/
        out.println(getRepairSQL(request,action));
    }
    /*action:0为删除，1位更新，2为添加，3为关注*/
    public boolean getRepairSQL(HttpServletRequest request,String action){
        repairSql sql = new repairSql();
        if(Objects.equals(action, "0")){/*删除工单*/
            return sql.repairDel(request);
        }else if(Objects.equals(action, "1")){/*更新工单*/
            boolean addStation = sql.repairStationInsert(request);/*添加工单处理过程*/
            boolean updateGd = sql.repairUpdate(request);
            if(updateGd&&addStation){
                return true;
            }else {
                return false;
            }
        }else if(Objects.equals(action, "2")){/*添加工单*/
            return sql.repairInsert(request);
        }else if(Objects.equals(action, "3")){/*关注工单*/
            return sql.repairUpdate(request);
        }
        return false;
    }
}


