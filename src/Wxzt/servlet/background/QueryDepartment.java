package Wxzt.servlet.background;

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
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/18.
 */
public class QueryDepartment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

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
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        String conditions = request.getParameter("Conditions");
        System.out.println("conditions="+conditions);
        if(conditions.equals("")){
            System.out.println("查询条件为空");
            strSQL = "";
            System.out.println("strSQL=="+strSQL);

        }else {
            System.out.println("查询条件不为空");
            strSQL = "and Groupnum = '"+conditions+"'or GroupName = '"+conditions+"'";
            System.out.println("查询条件"+strSQL);
        }
        if(currentPage<1){
            currentPage = 1;
        }
        int count1 =currentNum*(currentPage-1);

        sql = "SELECT a.groupid,a.groupnum,a.groupname,a.note,a.crm,b.jsmc as role FROM dddGroup as a" +
                "left join px_crm_jsqx as b on a.role = b.jsbh WHERE  1=1 "+strSQL+" ORDER BY Groupid limit "+count1+","+currentNum;
        JDBC jdbc = new JDBC();

        //查询表单数据数量
        String countSQL = "select count(*) from dddGroup where 1=1 "+strSQL;
        try {
            osCount = jdbc.executeQuery(countSQL);
            if (osCount.next()) {
                count = osCount.getInt(1);//表单数据数量
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
        //获取数据
        QueryTableJson jsonData = new QueryTableJson();
        if(count>0){
            try {
                // 执行查询
                System.out.println("*****"+sql);
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                    System.out.println("*****"+sql);

                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(os.getString("Groupid"));//0
                        data.add(os.getString("Groupnum"));//1
                        data.add(os.getString("GroupName"));//2
                        data.add(os.getString("note"));//3
                        data.add(os.getString("crm"));//5
                        data.add(os.getString("role"));//6
                        listData.add(data);
                    }
                    jsonData.setDataList(listData);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setDataType("Y");
                } catch (SQLException e) {
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

