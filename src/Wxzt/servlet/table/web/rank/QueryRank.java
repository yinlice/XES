package Wxzt.servlet.table.web.rank;

import Wxzt.servlet.bean.QueryTableJson;
import Wxzt.tool.JDBC;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2015/12/30.
 */
public class QueryRank extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        String DataTable = "";//数据表
        ResultSet os;//执行SQL的ResultSet

        String desc = "";//排序内容
        String dt = "";//查询时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //昨日日期
        Date d = new Date(System.currentTimeMillis()-1000*60*60*24);
        String Yesterday = formatter.format(d);
        //今日日期
        Date d_1 = new Date();
        String todayTime = formatter.format(d_1);

        //获取jQuery传递的参数
        String rankType = request.getParameter("rankType");//排序方式
        /*设置排序方式*/
        if(Objects.equals(rankType, "0")){//按注册量排序
            desc = "order by SumIsRegister desc";
        }else if(Objects.equals(rankType, "1")){//按充值量排序
            desc = "order by SumIsTopUp desc";
        }else if(Objects.equals(rankType, "2")){//按投资量排序
            desc = "order by SumIsInvest desc";
        }else if(Objects.equals(rankType, "3")){//按呼叫量排序
            desc = "order by countCallout desc";
        }

        String firstDT = request.getParameter("firstDt");//查询开始时间 min
        String endDT = request.getParameter("endDt");//查询开始时间 max
        if(firstDT.equals("")){
            firstDT = Yesterday;//未选查询开始时间，即默认为昨天
        }
        if(endDT.equals("")){
            endDT = todayTime;//未选查询结束时间，即默认为今天
        }
        /*设置查询时间*/
        dt = " between '"+firstDT+"' and '"+endDT+"'";

        String group = request.getParameter("group");//工作组

        DataTable = "select a.WorknumOwner, sum(a.IsInvest)/2 as SumIsInvest,sum(a.IsRegister) as SumIsRegister,\n" +
                "sum(a.IsTopUp) as SumIsTopUp,sum(countCallout) as countCallout,\n" +
                "c.workName\n" +
                "from px_CRM_Cust as a, \n" +
                "(select sum(countCallout) as countCallout ,worknum\n" +
                "from dddReport_Work  \n" +
                "where dt "+dt+"\n" +
                "group by worknum) as b,dddWork as c\n" +
                "where a.IsInvest = 2 \n" +
                "and a.dtInvest "+dt+" \n" +
                "and a.WorknumOwner in \n" +
                "(select worknum from dddWork as e left join dddGroup as f on e.groupid=f.groupid where f.groupName='"+group+"')\n" +
                "and a.WorknumOwner = b.worknum\n" +
                "and a.WorknumOwner = c.worknum\n" +
                "group by a.WorknumOwner,c.workName " +desc;
        System.out.println("DataTable="+DataTable);

        JDBC jdbc = new JDBC();
        QueryTableJson jsonData = new QueryTableJson();
        //获取数据
        try {
            os = jdbc.executeQuery(DataTable);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                int count = 0;
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("workName"));//坐席姓名-0
                    data.add(group);//部门-1
                    data.add(os.getString("countCallout"));//外呼量-2
                    data.add(os.getString("SumIsInvest"));//投资量-3
                    data.add(os.getString("SumIsRegister"));//注册量-4
                    data.add(os.getString("SumIsTopUp"));//充值量-5
                    listData.add(data);
                    count++;
                }
                jsonData.setDataList(listData);
                jsonData.setCount(count);
                jsonData.setCountPage(1);
                jsonData.setCurrentPage(1);
                jsonData.setDataType("Y");
                System.out.println("ok");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}




