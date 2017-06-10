package Wxzt.servlet.table.web;

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
import java.util.Objects;

/**
 * Created by Administrator on 2015/10/19.
 */
public class QueryDuty extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        System.out.println("*********QueryDuty");
        int count = 0;//数据总数
        int countPage;//总页面数
        int currentPage;//当前页数
        int currentNum = 50;//页面显示最大数据

        String strSQL="";//查询条件
        String sql ;//查询语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet
        String DataTable = " (select a.id,a.WorkOID,a.Weekly,a.OnDutyName,a.OnDutyTelnum,b.GroupName " +
                "from dddOnDuty as a left join dddGroup as b on a.WorkOID=b.Groupnum) as c ";//数据表
        //获取jQuery传递的参数
        String ConditionsSelect = request.getParameter("ConditionsSelect");//查询框的选项
        String Conditions = request.getParameter("Conditions");//查询框的数据
        System.out.println("conditions="+Conditions);
        if(!Objects.equals(Conditions,"")&&!Objects.equals(Conditions,null)){
            if(ConditionsSelect.equals("0")){//全部
                strSQL += "and (OnDutyName = '"+Conditions+"' or OnDutyTelnum = '"+Conditions+"')";
            }else if(ConditionsSelect.equals("1")){//坐席
                strSQL += "and (OnDutyName = '"+Conditions+"')";
            }else if(ConditionsSelect.equals("2")){//分机
                strSQL += "and (OnDutyTelnum = '"+Conditions+"')";
            }
        }
        String group = request.getParameter("group");//工作组选项
        if(!group.equals("allGroup")){
            strSQL += "and WorkOID='"+group+"'";
        }
        //权限判定
        String IsMgr = request.getParameter("IsMgr");//权限:0为无；1为本人；2为本部门；3为公司
        if(IsMgr.equals("2")||IsMgr.equals("1")){
            System.out.println("坐席权限");
            String groupIsMgr = request.getParameter("groupIsMgr");
            strSQL += "and WorkOID='"+groupIsMgr+"'";
        }
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        int count1 = currentNum*(currentPage-1);

        sql = "SELECT  * FROM "+DataTable+" " +
                "WHERE 1=1 "+strSQL+" ORDER BY ID DESC limit "+count1+","+currentNum;
        System.out.println("QueryDuty="+sql);

        JDBC jdbc = new JDBC();
        //查询表单数据数量
        String countSQL = "select count(*) from "+DataTable+" where 1=1"+strSQL;
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
        //获取数据
        QueryTableJson jsonData = new QueryTableJson();
        if(count>0){
            try {
                // 执行查询
//                System.out.println(SQLstate);
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();

                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(os.getString("id"));
                        data.add(os.getString("GroupName"));//工作组
                        data.add(os.getString("Weekly"));//星期
                        data.add(os.getString("OnDutyName"));//值班人姓名
                        data.add(os.getString("OnDutyTelnum"));//值班电话
                        data.add(os.getString("WorkOID"));//工作组ID
                        listData.add(data);
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
