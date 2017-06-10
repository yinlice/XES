package Wxzt.servlet.table.web.knowledge;

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
 * Created by Administrator on 2015/12/14.
 */
public class QueryKnowledge extends HttpServlet {
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

        String conditions;//输入条件
        String strSQL="";//查询条件
        String sql ;//查询语句
        String DataTable = "px_CRM_KnowledgeBase";//数据表
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet

        //获取jQuery传递的参数
        conditions = request.getParameter("Conditions");
        System.out.println("conditions="+conditions);
        if(!conditions.equals("")){
            System.out.println("查询条件不为空");
            strSQL += "and (KB_Title like '%"+conditions+"%' or KB_Content like '%"+conditions+"%')";
        }
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        int currentCount = currentNum * (currentPage-1);//已显示的数据
        sql = "SELECT ID,KB_Category,KB_Title FROM " +DataTable+
                " WHERE 1=1 "+strSQL+" ORDER BY ID DESC limit "+currentCount+","+currentNum;
        System.out.println("SQL="+sql);

        JDBC jdbc = new JDBC();
        //查询表单数据数量
        String countSQL = "select count(*) from "+DataTable+" where 1=1"+strSQL;
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
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(os.getString("ID"));//ID-0
                        data.add(os.getString("KB_Category"));//类别-1
                        data.add(os.getString("KB_Title"));//标题-2
//                        data.add(os.getString("KB_Content"));//内容-3
                        listData.add(data);
                    }
                    jsonData.setDataList(listData);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setDataType("Y");
                    System.out.println("ok");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                jdbc.closeConnection();
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



