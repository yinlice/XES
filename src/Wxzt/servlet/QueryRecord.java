package Wxzt.servlet;

import Wxzt.servlet.bean.QueryTableJson;
import Wxzt.tool.JDBC;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QueryRecord extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        //设置当前时间
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //创建session对象
        HttpSession session = request.getSession();

        String TelTime ;//是否选中呼叫时间参数
        String Extension ;//是否选中分机号参数
        String TelStartTime ;//呼叫开始时间
        String TelEndTime ;//呼叫结束时间
        String ExtensionNum ;//分机号

        int count = 0;//数据总数
        int countPage;//总页面数
        int currentPage;//当前页数
        int currentNum = 50;//页面显示最大数据

        String strSQL = " ";//查询条件
        String sql ;//查询语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet
//        String outAjax = "";//返回信息
//        String outAjaxPage ;//显示总页面数和当前页面

        //获取jQuery传递的参数
        TelTime = request.getParameter("TelTime");
        Extension = request.getParameter("Extension");
        System.out.println("********"+Extension);
        currentPage = Integer.parseInt(request.getParameter("currentPage"));

        //判断是否传值
        if(TelTime.equals("true")){
            TelStartTime = (!request.getParameter("TelStartTime").equals(""))?request.getParameter("TelStartTime"):format.format(date);
            TelEndTime = (!request.getParameter("TelEndTime").equals(""))?request.getParameter("TelEndTime"):format.format(date);
            strSQL += " and StartDateTime between '"+TelStartTime+"' and  '"+TelEndTime+"'";
        }
        if(Extension.equals("true")){
//            ExtensionNum = (!request.getParameter("ExtensionNum").equals(""))?request.getParameter("ExtensionNum"):"0";
//            ExtensionNum = "801";
            ExtensionNum = request.getParameter("ExtensionNum");
            strSQL += "and ExtNum = '"+ExtensionNum+"'";
            System.out.println("^^^^^^^^^^^^"+strSQL);
        }
        if(currentPage<1){
            currentPage = 1;
        }
        int count1 = currentNum*(currentPage-1);

        sql = "SELECT  * FROM WX_PBX_CALL " +
                "WHERE 1=1 "+strSQL+" ORDER BY ID DESC limit "+count1+","+currentNum;
        System.out.println(sql);

        JDBC jdbc = new JDBC();

        //查询表单数据数量
        String countSQL = "select count(*) from WX_PBX_CALL where 1=1"+strSQL;
        try {
            osCount = jdbc.executeQuery(countSQL);
            if (osCount.next()) {
                count = osCount.getInt(1);//表单数据数量
                System.out.println("count========"+count);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        //获取总页数countPage
        if (count%currentNum==0) {
            countPage=count/currentNum;
        }else {
            countPage=count/currentNum+1;
        }
        //将当前页数放置到session中
        session.setAttribute("countPage", countPage);
        //获取数据
        QueryTableJson jsonData = new QueryTableJson();
        if(count>0){
            try {
                // 执行查询
                System.out.println(sql);
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();

                    int i = 1;
                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(i + "");
                        data.add(os.getString("CallID"));
                        data.add(os.getString("StartDateTime"));
                        data.add(os.getString("CallType"));
                        data.add(os.getString("ExtNum"));
                        listData.add(data);
                        i++;
                    }
                    jsonData.setDataList(listData);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setDataType("Y");
                } catch (SQLException e) {
                    System.out.println("+++++++++++++++++");
                    e.printStackTrace();
                }
                jdbc.closeConnection();
//                out.println(outAjax);//返回html信息
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No one");
            jsonData.setDataType("N");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);

    }
}
