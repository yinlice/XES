package Wxzt.servlet.Report;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Report.javabean.WorkBean;
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
public class QueryWork extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.print("tt--------------------");
        PrintWriter out = response.getWriter();

        int count = 0;//数据总数
        int countPage;//总页面数
        int currentPage;//当前页数
        int currentNum = 50;//页面显示最大数据

        String strSQL = "";//查询条件
        String sql ;//查询语句
        String countSQL;//查询数据数量语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet
        String DataTable;//数据表

        QueryTableJson jsonData = new QueryTableJson();//创建json对象
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        String group = request.getParameter("group");//查询工作组
        if (!Objects.equals(group, "all")){
            strSQL += " and GroupName = '"+group+"'";
        }
        //无效数据,当参数值等于2时，不显示无效数据
        String noData = request.getParameter("noData");
        if(noData.equals("2")){
            strSQL += " and worknum!='--' ";
        }
//        String buttonType = request.getParameter("buttonType");//查询种类
        String Conditions = request.getParameter("Conditions");//查询条件输入框信息
        if (Conditions!=null&& !Conditions.equals("")){
            strSQL += " and (Worknum = '" + Conditions + "' or WorkName = '" + Conditions + "')";
        }
        String year = request.getParameter("year");//查询年份
        if (!Objects.equals(year, "all")){
            String yearDayFirst = year+"-01-01";
            int AddYear = Integer.parseInt(year)+1;
            String yearDayEnd = AddYear+"-01-01";
            strSQL += " and dt between '"+yearDayFirst+"' and '"+yearDayEnd+"'";
        }
        String firstDT = request.getParameter("firstDt");//查询开始时间 min
        String endDT = request.getParameter("endDt");//查询结束时间 max
        String buttonType = request.getParameter("buttonType");//按钮类型
        if (buttonType.equals("Yesterday")){
            strSQL+=" and dt ='"+Yesterday+"'";
        }
        if(firstDT!=null&&!firstDT.equals("")){
            strSQL+=" and dt !<'"+firstDT+"'";
        }
        if(endDT!=null&&!endDT.equals("")){
            strSQL+=" and dt!>'"+endDT+"'";
        }

        JdbcForCti jdbc = new JdbcForCti();
        String code = jdbc.getCode();
        strSQL = (code==null|| Objects.equals(code, ""))?strSQL:(strSQL+" and companyCode ='"+code+"'");

        DataTable = "(select max(id) as id,Worknum,WorkName,GroupName," +
                "sum(CountCallIn) as CountCallIn," +
                "sum(CountCallOut) as CountCallOut," +
                "sum(CountCallInFail) as CountCallInFail," +
                "sum(CountCallOutFail) as CountCallOutFail," +
                "(sum(CountCallOut)-sum(CountCallOutFail)) AS CountCallOutEffective," +
                "(cast(sum(CountCallIn) as signed)-cast(sum(CountCallInFail) as signed)) as CallIn," +
                "CAST(sum(TimeLenIdle)/60.00 AS DECIMAL(18,2)) as TimeLenIdle," +
                "CAST(sum(CallinTimeLenSpeak)/60.00 AS DECIMAL(18,2)) as CallinTimeLenSpeak," +
                "CAST(sum(CalloutTimeLenSpeak)/60.00 AS DECIMAL(18,2)) as CalloutTimeLenSpeak,\n" +
                "CAST(sum(TimeLenBusy)/60.00 AS DECIMAL(18,2)) as TimeLenBusy, " +
                "CAST(sum(TimeLenRest)/60.00 AS DECIMAL(18,2)) as TimeLenRest, " +
                "CAST(sum(TimeLenEat)/60.00 AS DECIMAL(18,2)) as TimeLenEat , " +
                "CAST(sum(CallinTimeLenRing)/60.00 AS DECIMAL(18,2)) as CallinTimeLenRing, " +
                "CAST(sum(CallinTimeLenSpeak+CalloutTimeLenSpeak+CallinTimeLenRing+TimeLenIdle)/60.00 AS DECIMAL(18,2)) as onlineTime,"+
                "max(DATE_FORMAT(dt,'%Y-%m-%d') ) as endDT, " +
                "min(DATE_FORMAT(dt,'%Y-%m-%d') ) as firstDT, " +
                "min(DATE_FORMAT(dt,'%Y-%m-%d') ) +' 到 '+max(DATE_FORMAT(dt,'%Y-%m-%d') ) as dt, " +
                "sum(CountSatisAgentNo) as CountSatisAgentNo,\n" +
                "sum(CountSatisVery) as CountSatisVery, " +
                "sum(CountSatisGeneral) as CountSatisGeneral, " +
                "sum(CountSatisBad) as CountSatisBad, " +
                "sum(CountSatisCustNo) as CountSatisCustNo, " +
                "min(dtWorkStart) as dtWorkStart, " +
                "max(dtWorkEnd) as dtWorkEnd " +
                "from dddReport_Work where 1=1 "+strSQL+" group by Worknum,WorkName,GroupName) as a ";//数据表
        int count1 = currentNum*(currentPage-1);

        sql = "SELECT  * FROM "+DataTable+
                "WHERE 1=1 ORDER BY ID DESC limit "+count1+","+currentNum;
        System.out.println("SQLstate========"+sql);
        //查询表单数据数量
        countSQL = "select count(*) from "+DataTable;
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
        if(count>0){
            try {
                // 执行查询
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                    while (os.next()) {
                        ArrayList<String> data = new ArrayList<String>();
                        data.add(os.getString("id"));//0
                        data.add(os.getString("dt"));//1日期
                        data.add(os.getString("Worknum"));//2工号
                        data.add(os.getString("WorkName"));//3坐席姓名
                        data.add(os.getString("GroupName"));//4工作组
                        data.add(os.getString("CountCallIn"));//5来电量
                        data.add(os.getString("CountCallOut"));//6外拨量
                        data.add(os.getString("CountCallOutFail"));//7外拨呼损量
                        data.add(os.getString("TimeLenIdle"));//8空闲时间
                        data.add(os.getString("onlineTime"));//9在线时长=通话时长（呼入呼出总和）+空闲+话后+振铃+在线保持时长
                        data.add(os.getString("CallinTimeLenSpeak"));//10接听时长
                        data.add(os.getString("CalloutTimeLenSpeak"));//11外拨时长
                        data.add(os.getString("TimeLenBusy"));//12置忙时长
                        data.add(os.getString("TimeLenRest"));//13休息时长
                        data.add(os.getString("TimeLenEat"));//14吃饭时长
                        data.add(os.getString("CallinTimeLenRing"));//15振铃时长
                        data.add(os.getString("CallIn"));//16接听总量=呼入总量-呼入呼损
                        data.add(os.getString("CountSatisAgentNo"));//17-坐席未请求
                        data.add(os.getString("CountSatisVery"));//18-非常满意
                        data.add(os.getString("CountSatisGeneral"));//19-一般
                        data.add(os.getString("CountSatisBad"));//20-不满意
                        data.add(os.getString("CountSatisCustNo"));//21-坐席未评价
                        data.add(os.getString("dtWorkStart"));//22-上班时间
                        data.add(os.getString("dtWorkEnd"));//23-下班时间
                        data.add(os.getString("CountCallOutEffective"));//24-有效外拨
                        data.add(os.getString("CountCallInFail"));//25来电呼损量

                        listData.add(data);
                    }
                    System.out.println("创建数据完成");
                    jsonData.setDataList(listData);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setDataType("Y");
                    /*添加导出excel语句到相应的session*/
                    HttpSession session = request.getSession();
                    Query query = new Query();
                    session.setAttribute("reportWork" + "ToExcel", query.setExcelSql(DataTable, "", "id"));
                    jsonData.setTableName("reportWork");
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
                String[] listTotal = {"countCallIn","countCallInFail","countCallOut","countCallOutFail","timeLenIdle","onlineTime","callinTimeLenSpeak",
                        "calloutTimeLenSpeak","timeLenBusy","timeLenRest","timeLenEat","callinTimeLenRing","callIn","countCallOutEffective",
                        "countSatisAgentNo","countSatisVery","countSatisGeneral","countSatisBad","countSatisCustNo"};
                commonReport cr = new commonReport();
                String sqlTotal = cr.getSql(listTotal, DataTable);
                System.out.println("查询坐席报表统计数据：\n"+sqlTotal);
                jsonData.setTotalList(cr.sqlListData(new WorkBean(), sqlTotal, listTotal));
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
