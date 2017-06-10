package Wxzt.servlet.Report;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Report.javabean.HourBean;
import Wxzt.servlet.bean.QueryTableJson;
import Wxzt.tool.JdbcForCti;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2015/11/3.
 */
public class ByHour extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        int count = 0;//数据总数
        int countPage;//总页面数
        int currentPage;//当前页数
        int currentNum = 50;//页面显示最大数据

        String strSQL = "";//查询条件
        String countSQL;//查询数据语句
        String sql ;//查询语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        //今日日期
        Date d_1 = new Date();
        String todayTime = formatter.format(d_1);
        //昨日日期
        Date d = new Date(System.currentTimeMillis()-1000*60*60*24);
        String Yesterday = formatter.format(d);
        //获取jQuery传递的参数
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        String firstDT = request.getParameter("firstDt");//查询开始时间 min
        String endDT = request.getParameter("endDt");//查询结束时间 max
        String buttonType = request.getParameter("buttonType");//按钮类型
        if (buttonType.equals("Yesterday")){
            strSQL+=" and dt >='"+Yesterday+"'";
        }
        if(firstDT!=null&&!firstDT.equals("")){
            strSQL+=" and dt >='"+firstDT+"'";
        }
        if(endDT!=null&&!endDT.equals("")){
            strSQL+=" and dt <= '"+endDT+"'";
        }
        JdbcForCti jdbc = new JdbcForCti();
        String code = jdbc.getCode();
        strSQL = (code==null|| Objects.equals(code, ""))?strSQL:(strSQL+" and companyCode ='"+code+"'");
        int count1 = currentNum*(currentPage-1);
        sql = "SELECT  DATE_FORMAT(dt,'%Y-%m-%d') as dt,CountCallIn,CountCallOut,REPEAT('=',(CountCallin)) as barChart FROM dddReport_Hour "+
                "WHERE 1=1 "+strSQL+" ORDER BY id DESC limit "+count1+","+currentNum;
        System.out.println("每小时报表="+sql);

        QueryTableJson jsonData = new QueryTableJson();//创建json对象
        //查询表单数据数量
        countSQL = "select count(*) from dddReport_Hour where 1=1 "+strSQL;
        try {
            osCount = jdbc.executeQuery(countSQL);
            if (osCount.next()) {
                count = osCount.getInt(1);//表单数据数量
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        //获取总页数countPage
        countPage = (count%currentNum==0)?count/currentNum:count/currentNum+1;
        //获取数据
        if(count>0){
            try {
                // 执行查询
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData_1 = new ArrayList<ArrayList>();
                    while (os.next()) {
                        ArrayList<String> data = new ArrayList<String>();
                        data.add(os.getString("dt"));//0日期
                        data.add(String.valueOf(os.getInt("CountCallIn")));//1-呼入数量
                        data.add(String.valueOf(os.getInt("CountCallOut")));//2-呼出数量
                        data.add(os.getString("barChart"));//3-呼入量条形图
                        listData_1.add(data);
                    }
                    System.out.println("创建数据完成");
                    jsonData.setDataList(listData_1);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setTableName("dddReport_Work where 1=1 " + strSQL);
                    jsonData.setDataType("Y");
                    /*添加导出excel语句到相应的session*/
                    HttpSession session = request.getSession();
                    Query query = new Query();
                    session.setAttribute("reportOfHour" + "ToExcel", query.setExcelSql("dddReport_Work", strSQL, "id"));
                    jsonData.setTableName("reportOfHour");
                } catch (SQLException e) {
                    System.out.println("+++++++++++++++++");
                    e.printStackTrace();
                }
                jdbc.closeConnection();
//                out.println(outAjax);//返回html信息
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            /*获取报表统计数据*/
            if(currentPage==1){
                String[] listTotal = {"countCallIn","countCallOut"};
                commonReport cr = new commonReport();
                String sqlTotal = cr.getSql(listTotal, " (select * from dddReport_Hour where 1=1 "+strSQL+") as a ");
                System.out.println("查询坐席报表统计数据：\n"+sqlTotal);
                jsonData.setTotalList(cr.sqlListData(new HourBean(), sqlTotal, listTotal));
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


