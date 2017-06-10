package Wxzt.servlet.table.web.knowledge;

import Wxzt.servlet.bean.QueryJsonData;
import Wxzt.servlet.bean.QueryTableJson;
import Wxzt.tool.JDBC;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/23.
 */
public class QueryKnowledgeView extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        String sql = "select * from px_CRM_KnowledgeBase";//查询语句
        ResultSet os;//执行SQL的ResultSet

        //获取jQuery传递的参数
        String knowledge_select = request.getParameter("knowledge_select");//查询条件
        if(!knowledge_select.equals("")){
            sql += " where KB_Category like '%"+knowledge_select+"%' or KB_Title like '%"+knowledge_select+"%' or KB_Content like '%"+knowledge_select+"%'";
        }
        System.out.println("SQL="+sql);

        JDBC jdbc = new JDBC();
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        try {
            os = jdbc.executeQuery(sql);
            try {
                int i = 0;
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("ID"));//ID-0
                    data.add(os.getString("KB_Category"));//类别-1
                    data.add(os.getString("KB_Title"));//标题-2
                    if(i<1){//添加第一条内容
                        ArrayList data_1 = new ArrayList();
                        data_1.add(os.getString("KB_Content"));//内容
                        jsonData.setDataList_O(data_1);
                    }
                    i++;
                    listData.add(data);
                }
                jsonData.setDataList(listData);
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




