package Wxzt.servlet.ToPlayScreen;

import Wxzt.servlet.Invest.investSql;
import Wxzt.servlet.RepairOrder.repairSql;
import Wxzt.servlet.dynamic.dynamicSql;
import Wxzt.tool.JDBC;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2015/11/9.
 */
public class updataScreen extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println("------------------updataScreen");

        String sql=" ";//查询语句
        JDBC jdbc = new JDBC();//用于调用操作数据库接口的对象
        screenBean screen = new screenBean();
        /*传递的参数*/
        String todayTime = request.getParameter("todayTime");//今日时间
        System.out.println(todayTime);
        String tabsID = request.getParameter("tabsID");//判断待操作的tab类型
        String action = request.getParameter("action");//0为删除;1为更新;2为添加
        String CustNum = request.getParameter("custNum");//对应用户编号
        String InvestAmount = request.getParameter("investAmount");//投资金额
        String worknum = request.getParameter("worknum");//添加人工号
        String uuid = request.getParameter("uuid");//uuid
        /*用于客户信息更新或者添加操作*/
//        updataCust updatacust = new updataCust();
//        screen.setScreenResult(updatacust.getCustSQL(request, action));
        /*添加投资记录*/
        if(!Objects.equals(InvestAmount, "")){
            investSql inv = new investSql();
            screen.setInvestResult(inv.investInsert(request));
        }
        /*添加动态记录或者工单记录*/
        if(tabsID.equals("dynamicForm")&&!request.getParameter("custDynamicInfo").equals("")){//添加动态
            dynamicSql dyn = new dynamicSql();
            screen.setInvestResult(dyn.dynUpdata(request));
        }else if(tabsID.equals("repairOrderFrom")){//添加工单
            repairSql rep = new repairSql();
            screen.setInvestResult(rep.repairUpdate(request));
        }
        /*更新录音文件*/
//        sql = "update dddCdr set CallOID='"+CustNum+"' where uuid = '"+uuid+"'";
//        screen.setCdrResult(jdbc.executeUpdate(sql));
        jdbc.closeConnection();
        /*返回数据*/
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(screen);
        System.out.println(json);
        out.println(json);
    }
}

