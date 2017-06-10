package Wxzt.servlet;

import Wxzt.servlet.bean.QueryJsonData;
import Wxzt.tool.IniReader;
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
 * Created by Administrator on 2015/10/9.
 */
public class getWorker extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("getWorker------------");
        PrintWriter out = response.getWriter();

        String sql;//查询用户基础信息
        ResultSet os;//记录用户基础信息的ResultSet

        String workNum = request.getParameter("workNum");//工号
        //获取ini文件
        IniReader reader = new IniReader();
        String recordUrl = reader.getValue("Record", "url");
        String outsideUrl = reader.getValue("outside","URL");

        JDBC jdbc = new JDBC();
        sql = "select id,workname,worknum,sipnum,a.Groupid,roleid,QXLB,IsMgr,PassNumber,c.GroupName from dddWork as a\n" +
                "left join px_CRM_JSQX as b on a.roleid = b.JSBH \n" +
                "left join dddGroup as c on a.Groupid = c.Groupnum\n"+
                "where a.worknum="+workNum;
        System.out.println("sql_Loading="+sql);
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        try {
            // 执行查询
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("workname"));//0姓名
                    data.add(os.getString("worknum"));//1工号
                    data.add(os.getString("sipnum"));//2分机号
                    data.add(os.getString("Groupid"));//3部门
                    data.add(os.getString("roleid"));//4角色
                    data.add(os.getString("QXLB"));//5菜单列表
                    data.add(os.getString("IsMgr"));//6范围
                    data.add(os.getString("id"));//7
                    data.add(recordUrl);//8录音文件URL
                    data.add(os.getString("PassNumber"));//9-外显号码
                    data.add(outsideUrl);//10-点击外呼URL
                    data.add(os.getString("GroupName"));//11-工作组
                    listData.add(data);
                }

                jsonData.setDataList(listData);
                jsonData.setDataType("Y");
            } catch (SQLException e) {
                jsonData.setDataType("N");//无数据
                System.out.println("+++++++++++++++++");
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            jsonData.setDataType("N");//无数据
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}
