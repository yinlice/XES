package Wxzt.servlet.Login;

import Wxzt.tool.JDBC;
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
import java.util.Objects;

/**
 * Created by Administrator on 2016-4-25.
 */
public class menuControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public String code = "";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JDBC jdbc = new JDBC();
        MenuGetData getData = new MenuGetData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //今日日期
        Date d = new Date(System.currentTimeMillis());
        String today = formatter.format(d);
        //昨日日期
        Date d_1 = new Date(System.currentTimeMillis()-1000*60*60*24);
        String Yesterday = formatter.format(d_1);
        //前日日期
        Date d_2 = new Date(System.currentTimeMillis()-1000*60*60*24*2);
        String beforeOfYesterday = formatter.format(d_2);
        //获取菜单查询语句
        String nav = request.getParameter("nav");
        if(nav!=null){
            try {
                UserNavSQL(jdbc, setUserNavSQL(nav), getData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String Worknum = request.getParameter("Worknum");
        if(Worknum!=null){
            try {
                jdbc = new JdbcForCti();
                JdbcForCti j = new JdbcForCti();
                code = j.getCode();
                userDataSQL(jdbc, setUserDataSQL(Worknum,today,Yesterday,beforeOfYesterday), getData);
                callinListSQL(jdbc,setCallinListSQL(today),getData);
                calloutListSQL(jdbc,setCalloutListSQL(today),getData);
                callAverageListSQL(jdbc,setCallAverageListSQL(today),getData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        jdbc.closeConnection();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getData);
        System.out.println(json);
        out.println(json);
    }
    /*设置获取坐席对应菜单数据的sql语句*/
    public String setUserNavSQL(String nav){
        if(nav.equals("all")){
            return  "select * from px_CRM_Menu order by OrderID";
        }else {
            return "select * from px_CRM_Menu where MenuID in ("+nav+") order by OrderID";
        }
    }
    /*获取菜单数据*/
    public void UserNavSQL(JDBC jdbc,String sql,MenuGetData getData) throws IOException, SQLException {
        System.out.println("查询菜单选项：\n"+sql);
        ResultSet os = jdbc.executeQuery(sql);
        ArrayList<Object> menu = new ArrayList<Object>();
        ArrayList<Object> thumbnail = new ArrayList<Object>();
        while (os.next()) {
            MenUNavBean nav_1 = new MenUNavBean();
            nav_1.setParentID(os.getString("ParentID"));
            nav_1.setThumbnailID(os.getString("MenuID"));
            nav_1.setThumbnailName(os.getString("MenuName"));
            nav_1.setThumbnailURL(os.getString("URL"));
            nav_1.setThumbnailOrder(os.getString("OrderID"));
            nav_1.setThumbnailNote(os.getString("Note"));
            if(Objects.equals(os.getString("ParentID"), "00")){
                menu.add(nav_1);
            }else {
                thumbnail.add(nav_1);
            }
        }
        getData.setMenu(menu);
        getData.setThumbnail(thumbnail);
    }
    /*设置获取坐席通话记录数据的sql语句*/
    public String setUserDataSQL(String Worknum,String today,String Yesterday,String beforeOfYesterday) throws IOException {
        return  "select DATE_FORMAT(dt,'%Y-%m-%d') as dt,CountCallIn,CallinTimeLenSpeak,(CountCallIn-CountCallInFail) as CallInSucc,"
                +"CountCallOut,CalloutTimeLenSpeak,(CountCallOut-CountCallOutFail) as CallOutSucc "
                +"from dddReport_Work where companyCode='"+code+"' and Worknum='"+Worknum+"' and  dt in('"+today+"','"+Yesterday+"','"+beforeOfYesterday+"') order by dt desc ";
    }
    /*获取坐席通话记录数据*/
    public void userDataSQL(JDBC jdbc,String sql,MenuGetData getData) throws IOException, SQLException {
        System.out.println("查询坐席数据：\n"+sql);
        ResultSet os = jdbc.executeQuery(sql);
        ArrayList<Object> userCall = new ArrayList<Object>();
        while (os.next()) {
            MenuUserCallBean call = new MenuUserCallBean();
            call.setCallDate(os.getString("dt"));
            call.setCallInNum(os.getString("CountCallIn"));
            call.setCallInSucc(os.getString("CallInSucc"));
            call.setCallInAverage(os.getString("CallinTimeLenSpeak"));
            call.setCallOutNum(os.getString("CountCallOut"));
            call.setCallOutSucc(os.getString("CallOutSucc"));
            call.setCallOutAverage(os.getString("CalloutTimeLenSpeak"));
            userCall.add(call);
        }
        getData.setUserCall(userCall);
    }
    /*设置获取坐席呼入排行的sql语句*/
    public String setCallinListSQL(String today){
        return "select  WorkName,(CountCallIn-CountCallInFail) as CountCallIn \n" +
                "from dddReport_Work where companyCode='"+code+"' and dt ='"+today+"' and WorkName !='' order by CountCallIn desc limit 10";
    }
    /*获取坐席呼入排行数据*/
    public void callinListSQL(JDBC jdbc,String sql,MenuGetData getData) throws IOException, SQLException {
        System.out.println("查询坐席呼入排行数据：\n"+sql);
        ResultSet os = jdbc.executeQuery(sql);
        ArrayList<String> userName = new ArrayList<String>();
        ArrayList<String> userData = new ArrayList<String>();
        MenuListBean in = new MenuListBean();
        while (os.next()) {
            userName.add(os.getString("WorkName"));
            userData.add(os.getString("CountCallIn"));
        }
        in.setUserNames(userName);
        in.setUserData(userData);
        getData.setCallinList(in);
    }
    /*设置获取坐席呼出排行的sql语句*/
    public String setCalloutListSQL(String today){
        return "select  WorkName,(CountCallOut-CountCallOutFail) as CountCallOut \n" +
                "from dddReport_Work where companyCode='"+code+"' and dt ='"+today+"' and WorkName !='' order by CountCallOut desc limit 10";
    }
    /*获取坐席呼出排行数据*/
    public void calloutListSQL(JDBC jdbc,String sql,MenuGetData getData) throws IOException, SQLException {
        System.out.println("查询坐席呼出排行数据：\n"+sql);
        ResultSet os = jdbc.executeQuery(sql);
        ArrayList<String> userName = new ArrayList<String>();
        ArrayList<String> userData = new ArrayList<String>();
        MenuListBean out_1 = new MenuListBean();
        while (os.next()) {
            userName.add(os.getString("WorkName"));
            userData.add(os.getString("CountCallOut"));
        }
        out_1.setUserNames(userName);
        out_1.setUserData(userData);
        getData.setCalloutList(out_1);
    }
    /*设置获取坐席平均通话排行的sql语句*/
    public String setCallAverageListSQL(String today){
        return "select  WorkName,CallinTimeLenSpeak\n" +
                "from dddReport_Work where companyCode='"+code+"' and dt ='"+today+"' and WorkName !='' order by CallinTimeLenSpeak desc limit 10";
    }
    /*获取坐席平均通话排行数据*/
    public void callAverageListSQL(JDBC jdbc,String sql,MenuGetData getData) throws IOException, SQLException {
        System.out.println("查询坐席平均通话排行数据：\n"+sql);
        ResultSet os = jdbc.executeQuery(sql);
        ArrayList<String> userName = new ArrayList<String>();
        ArrayList<String> userData = new ArrayList<String>();
        MenuListBean Average = new MenuListBean();
        while (os.next()) {
            userName.add(os.getString("WorkName"));
            userData.add(os.getString("CallinTimeLenSpeak"));
        }
        Average.setUserNames(userName);
        Average.setUserData(userData);
        getData.setAverageList(Average);
    }
}


