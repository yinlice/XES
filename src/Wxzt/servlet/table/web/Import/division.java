package Wxzt.servlet.table.web.Import;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2015/11/24.
 */
public class division extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("---------------------division");
        PrintWriter out = response.getWriter();
        JDBC jdbc = new JDBC();

        String action = request.getParameter("action");//分配类型：0为公共池分配数据；1为数据再分配
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");//用于H5的时间
        String newTime = format.format(new Date());//用于h5的现在时间
        boolean str = false;
        String sql;//查询语句
        if(Objects.equals(action, "0")){//从公共池分配数据
            String[] worknumList = request.getParameter("worknumList").split(",");//待分配员工工号list
            String[] valueList = request.getParameter("valueList").split(",");//员工对应分配的数量list
            String advancedQuery = request.getParameter("advancedQuery");//待分配的批号
            System.out.println("advancedQuery1="+advancedQuery);
            if(advancedQuery==null|| Objects.equals(advancedQuery, "null")){
                advancedQuery = " ";
            }
            for(int i=0;i<worknumList.length;i++){
                sql = "update px_CRM_Cust set dtUpdate='"+newTime+"', dtGive='"+newTime+"',WorknumOwner='"+worknumList[i]+"' where Custid in " +
                        "(select Custid from px_CRM_Cust where 1=1 "+advancedQuery+" and WorknumOwner='0' limit "+valueList[i]+")";
                System.out.println("公共池分配数据语句"+i+"=="+sql);
                str = jdbc.executeUpdate(sql);
            }
        }else if(Objects.equals(action, "1")){
            String worknum = request.getParameter("worknum");//待分配的工号
            String CustNum = request.getParameter("CustNum");//待分配的客户编号列表
            sql = "update px_CRM_Cust set dtGive='"+newTime+"',dtUpdate='"+newTime+"',WorknumOwner='"+worknum+"' where Custid in ("+CustNum+")";
            System.out.println("客户再分配数据语句=="+sql);
            str = jdbc.executeUpdate(sql);
        }
        jdbc.closeConnection();
        out.println(str);
    }
}




