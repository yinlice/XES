package Wxzt.servlet;

import Wxzt.servlet.bean.BTJDepartment;
import Wxzt.servlet.bean.DetermineTableJson;
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
 * Created by Administrator on 2015/9/25.
 */
public class tableDepartment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();
        System.out.println("---------");

        int count = 0;//数据总数
        String strSQL = "1=1";//查询条件
        String order = "desc";
        String orderSQL="";
        String sql ;//查询语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet
        ObjectMapper mapper = new ObjectMapper();//设置转化json对象

        //获取jQuery传递的参数
        int limit = Integer.parseInt(request.getParameter("limit"));//页面显示最大数据
        int offset = Integer.parseInt(request.getParameter("offset"));//查询该数据之后的数据
        String search = request.getParameter("search");//查询条件
        String sort = request.getParameter("sort");//根据该数据来查询数据库的desc或asc
        order = request.getParameter("order");//显示顺序
        System.out.println(limit+"---"+offset+"===="+search+"----"+sort+"===="+order);

        if(!(search == null)){
            strSQL +=" and (ID like '%"+search+"%' or CompanyName like '%"+search+"%')";
        }
        System.out.println("---------there1");
        if(!(sort == null)){
            orderSQL = " order by "+sort+" "+order;
        }
        System.out.println("---------2");

        String sql_T = "select  * from px_CRM_Company "+
        "where ID not in (select ID from px_CRM_Company where "+strSQL+" ORDER BY  ID DESC limit "+offset+") and "+strSQL+" ORDER BY ID DESC limit "+limit;
        System.out.println("sql_T***="+sql_T);
        sql = "select * from ("+sql_T+")as a "+orderSQL;
        System.out.println("SQL***="+sql);
        JDBC jdbc = new JDBC();

        //查询表单数据数量
        String countSQL = "select count(*) from px_CRM_Company where "+strSQL;
        try {
            osCount = jdbc.executeQuery(countSQL);
            if (osCount.next()) {
                count = osCount.getInt(1);//表单数据数量
                System.out.println("count========"+count);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        //获取数据
        DetermineTableJson jsonData = new DetermineTableJson();
        if(count>0){
            try {
                // 执行查询
//                System.out.println(SQLstate);
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<Object> listData = new ArrayList<Object>();
                    while (os.next()) {
                        BTJDepartment data = new BTJDepartment();
                        data.setID(Integer.parseInt(os.getString("ID")));
                        data.setDepartment(os.getString("CompanyName"));
                        data.setNote(os.getString("CompanyTel"));
//                        String json_T = mapper.writeValueAsString(data);
                        listData.add(data);
                    }
                    jsonData.setTotal(count);
                    jsonData.setRows(listData);
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
            jsonData.setTotal(count);
        }
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}

