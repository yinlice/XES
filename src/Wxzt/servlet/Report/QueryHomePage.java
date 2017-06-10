package Wxzt.servlet.Report;

import Wxzt.servlet.bean.HomePage;
import Wxzt.tool.JdbcForCti;
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

/**
 * Created by Administrator on 2015/10/30.
 */
public class QueryHomePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("-------------------QueryHomePage");
        PrintWriter out = response.getWriter();
        HomePage HomeData = new HomePage();
        JdbcForCti jdbc = new JdbcForCti();

        String sql_A ;//查询语句
        String sql_B_1 ;//查询语句
        String sql_B_2 ;//查询语句
        String sql_B_3 ;//查询语句
        ResultSet os;//执行SQL的ResultSet
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //昨日日期
        Date d = new Date(System.currentTimeMillis()-1000*60*60*24);
        String Yesterday = formatter.format(d);
        //前日日期
        Date d_2 = new Date(System.currentTimeMillis()-1000*60*60*24*2);
        String beforeOfYesterday = formatter.format(d_2);
        //大前天日期
        Date d_3 = new Date(System.currentTimeMillis()-1000*60*60*24*3);
        String threeDaysOfToday = formatter.format(d_3);
        //获取jQuery传递的参数
        String Worknum = request.getParameter("Worknum");

        sql_A = "select DATE_FORMAT(dt,'%Y-%m-%d')  as dt,\n" +
                "(CountCallIn-CountCallInFail) as CountCallIn,\n" +
                " CountCallOut,\n" +
                "TimeLenBusy,\n" +
                "CallinTimeLenSpeak,CountSatisVery\n" +
                " from dddReport_Work where Worknum='"+Worknum+"' and  dt in('"+Yesterday+"','"+beforeOfYesterday+"','"+threeDaysOfToday+"') order by dt desc ";
        sql_B_1="select  WorkName,(CountCallIn-CountCallInFail) as CountCallIn \n" +
                "from dddReport_Work where dt ='"+Yesterday+"' and WorkName !='' order by CountCallIn desc limit 10";
        sql_B_2="select  WorkName,(TimeLenIdle+TimeLenBusy+TimeLenRest+TimeLenEat) as TimeLen \n" +
                "from dddReport_Work where dt ='"+Yesterday+"' and WorkName !='' order by TimeLen desc limit 10";
        sql_B_3="select top 10 WorkName,CountSatisVery\n" +
                "from dddReport_Work where dt ='"+Yesterday+"' and WorkName !='' order by CountSatisVery desc limit 10";
        System.out.println(sql_A);
        System.out.println(sql_B_1);
        System.out.println(sql_B_2);
        System.out.println(sql_B_3);
        try {
            os = jdbc.executeQuery(sql_A);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
//                    data.add(os.getString("dt"));//0-时间
                    if(os.getString("dt").equals(Yesterday)){
                        data.add("0");//显示顺序-0
                    }else if(os.getString("dt").equals(beforeOfYesterday)){
                        data.add("1");//显示顺序-0
                    }else if(os.getString("dt").equals(threeDaysOfToday)){
                        data.add("2");//显示顺序-0
                    }
                    data.add(os.getString("CountCallIn"));//1-呼入量
                    data.add(os.getString("CountCallOut"));//呼出量-2
                    data.add(os.getString("TimeLenBusy"));//置忙时长-3
                    data.add(os.getString("CallinTimeLenSpeak"));//平均通话时长-4
                    data.add(os.getString("CountSatisVery"));//满意度-5
                    listData.add(data);
                }
                HomeData.setDataList_A(listData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            os = jdbc.executeQuery(sql_B_1);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("WorkName"));//呼入量-名称
                    data.add(os.getString("CountCallIn"));//呼入量-1
                    listData.add(data);
                }
                HomeData.setDataList_B_1(listData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            os = jdbc.executeQuery(sql_B_2);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("WorkName"));//呼入量-名称
                    data.add(os.getString("TimeLen"));//在线时长-1
                    listData.add(data);
                }
                HomeData.setDataList_B_2(listData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            os = jdbc.executeQuery(sql_B_3);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("WorkName"));//呼入量-名称
                    data.add(os.getString("CountSatisVery"));//满意度-1
                    listData.add(data);
                }
                HomeData.setDataList_B_3(listData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("ok");
            HomeData.setDataType("Y");
            HomeData.setUserNum(Worknum);
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(HomeData);
        System.out.println(json);
        out.println(json);
    }
}




