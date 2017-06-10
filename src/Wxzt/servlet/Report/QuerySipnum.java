package Wxzt.servlet.Report;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Report.javabean.SipnumBean;
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
 * Created by Administrator on 2015/10/2.
 */
public class QuerySipnum extends HttpServlet {
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
        String sql ;//查询语句
        String countSQL;//查询数据语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet
        String DataTable;//数据表

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
        String Conditions = request.getParameter("Conditions");//查询条件输入框信息
        if (!Objects.equals(Conditions,null)&&!Conditions.equals("")){
            strSQL += " and Sipnum = '" + Conditions + "'";
        }
        //无效数据,当参数值等于2时，不显示无效数据
        String noData = request.getParameter("noData");
        if(noData.equals("2")){
            strSQL += " and Sipnum!='--' ";
        }
        String firstDT = request.getParameter("firstDt");//查询开始时间 min
        String endDT = request.getParameter("endDt");//查询开始时间 max
        String buttonType = request.getParameter("buttonType");//按钮类型
        if (buttonType.equals("today")){
            firstDT = Yesterday;
        }

        JdbcForCti jdbc = new JdbcForCti();
        String code = jdbc.getCode();
        strSQL = (code==null|| Objects.equals(code, ""))?strSQL:(strSQL+" and companyCode ='"+code+"'");

        if (firstDT!=null&&!firstDT.equals("")){
            if(endDT==null||endDT.equals("")){
                endDT = Yesterday;//未选查询结束时间，即默认为今天
            }
            strSQL+=" and dt between '"+firstDT+"' and '"+endDT+"'";
            DataTable = "(SELECT '"+firstDT+" 到 "+endDT+"' as dt ,max(id) as id,Sipnum,sum(CountCallIn) as CountCallIn," +
                    "sum(CountCallInFail) as CountCallInFail,(sum(CountCallIn)-sum(CountCallInFail)) AS CountCallInEffective\n,sum(CountCallOut) as CountCallOut,sum(CountCallOutFail) as CountCallOutFail, " +
                    "(sum(CountCallOut)-sum(CountCallOutFail)) AS CountCallOutEffective\n,CAST(sum(CallinTimeLenSpeak)/60.00 AS DECIMAL(18,2)) as CallinTimeLenSpeak,CAST(sum(CalloutTimeLenSpeak)/60.00 AS DECIMAL(18,2)) as CalloutTimeLenSpeak " +
                    "FROM dddReport_Sipnum where length(Sipnum)<5  "+strSQL+" "+
                    "group by Sipnum ) as c ";//数据表
            int count1 = currentNum*(currentPage-1);
            sql = "SELECT * FROM "+DataTable+
                    " WHERE 1=1  ORDER BY ID DESC limit "+count1+","+currentNum;
            countSQL = "select count(*) from "+DataTable;
        }else {
            DataTable = " dddReport_Sipnum where length(Sipnum)<5 ";
            int count2 = currentNum*(currentPage-1);
            sql = "SELECT  id,dt,Sipnum,CountCallIn,CountCallInFail,CountCallOut,CountCallOutFail,CAST(CallinTimeLenSpeak/60.00 AS DECIMAL(18,2)) as CallinTimeLenSpeak,CAST(CalloutTimeLenSpeak/60.00 AS DECIMAL(18,2)) AS CalloutTimeLenSpeak," +
                    "(CountCallIn-CountCallInFail) AS CountCallInEffective,(CountCallOut-CountCallOutFail) AS CountCallOutEffective FROM "+DataTable+
                    ""+strSQL+" ORDER BY ID DESC limit "+count2+","+currentNum;
            countSQL = "select count(*) from "+DataTable+" "+strSQL;
            DataTable = "(select id,dt,Sipnum,CountCallIn,CountCallInFail,CountCallOut,CountCallOutFail,CAST(CallinTimeLenSpeak/60.00 AS DECIMAL(18,2)) as CallinTimeLenSpeak,CAST(CalloutTimeLenSpeak/60.00 AS DECIMAL(18,2)) AS CalloutTimeLenSpeak,(CountCallIn-CountCallInFail) AS CountCallInEffective,(CountCallOut-CountCallOutFail) AS CountCallOutEffective from dddReport_Sipnum where length(Sipnum)<5 "+strSQL+") as a ";//用于传递参数
        }
        //查询表单数据数量
        System.out.println("SQLstate--------------"+sql);
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
        QueryTableJson jsonData = new QueryTableJson();
        if(count>0){
            try {
                // 执行查询
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                    while (os.next()) {
                        ArrayList<String> data = new ArrayList<String>();
                        data.add(os.getString("id"));//0
                        if (firstDT!=null&&!firstDT.equals("")){//有输入时间
                            data.add(os.getString("dt"));//1日期
                        }else {
                            data.add(os.getString("dt").split(" ")[0]);//1日期
                        }
                        data.add(os.getString("Sipnum"));//2线路
                        data.add(os.getString("CountCallIn"));//3呼入数量
                        data.add(os.getString("CountCallInFail"));//4呼入损失
                        data.add(os.getString("CountCallOut"));//5呼出数量
                        data.add(os.getString("CountCallOutFail"));//6呼出损失数量
                        data.add(os.getString("CallinTimeLenSpeak"));//7呼入总时长
                        data.add(os.getString("CalloutTimeLenSpeak"));//8呼出总时长
                        data.add(os.getString("CountCallInEffective"));//9有效呼入
                        data.add(os.getString("CountCallOutEffective"));//10有效呼出
                        listData.add(data);
                    }
                    jsonData.setDataList(listData);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setDataType("Y");
                    /*添加导出excel语句到相应的session*/
                    HttpSession session = request.getSession();
                    Query query = new Query();
                    session.setAttribute("reportSipnum" + "ToExcel", query.setExcelSql(DataTable, "", "id"));
                    jsonData.setTableName("reportSipnum");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                jdbc.closeConnection();
//                out.println(outAjax);//返回html信息
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            /*获取报表统计数据*/
            if(currentPage==1){
                String[] listTotal = {"countCallIn","countCallInFail","countCallOut","countCallOutFail","callinTimeLenSpeak","calloutTimeLenSpeak",
                        "countCallInEffective", "countCallOutEffective"};
                commonReport cr = new commonReport();
                String sqlTotal = cr.getSql(listTotal, DataTable);
                System.out.println("查询分机报表统计数据：\n"+sqlTotal);
                jsonData.setTotalList(cr.sqlListData(new SipnumBean(), sqlTotal, listTotal));
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


