package Wxzt.servlet.Report;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Report.javabean.GroupBean;
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
 * Created by Administrator on 2015/10/20.
 */
public class QueryGroup extends HttpServlet {
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
        //无效数据,当参数值等于2时，不显示无效数据
        String noData = request.getParameter("noData");
        if(noData.equals("2")){
            strSQL += " and groupname !='' and groupname is not null ";
        }
        String group = request.getParameter("group");//查询工作组
        if (!Objects.equals(group, "all")){
            strSQL += " and GroupName = '"+group+"'";
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

        DataTable = "(SELECT max(id) as id,GroupName," +
                "sum(CountCallIn) as CountCallIn," +
                "sum(CountCallOut) as CountCallOut," +
                "(cast(sum(CountCallIn) as signed)-cast(sum(CountCallInFail) as signed)) as CallIn," +
                "sum(CountCallInFail) as CountCallInFail," +
                "sum(CountCallOutFail) as CountCallOutFail," +
                "(sum(CountCallOut)-sum(CountCallOutFail)) AS CountCallOutEffective, " +
                "CAST(sum(CallinTimeLenSpeak)/60.00 AS DECIMAL(18,2)) as CallinTimeLenSpeak, " +
                "sum(CountCallInAcd) as CountCallInAcd, " +
                "CAST(sum(CallinTimeLenAcd)/60.00 AS DECIMAL(18,2)) as CallinTimeLenAcd ," +
                //平均排队时长(acdAverageTime)
                "CAST((case when sum(CountCallInAcd)=0 then sum(CallinTimeLenAcd) " +
                "else cast(sum(CallinTimeLenAcd) as signed) end)/(60.00*sum(CountCallInAcd)) AS DECIMAL(18,2)) as acdAverageTime, " +
                "CAST(sum(TimeLenBusy)/60.00 AS DECIMAL(18,2)) as TimeLenBusy, " +
                "CAST(sum(TimeLenIdle)/60.00 AS DECIMAL(18,2)) as TimeLenIdle, " +
                "CAST(sum(CallinTimeLenRing)/60.00 AS DECIMAL(18,2)) as CallinTimeLenRing, " +
                "CAST(sum(CalloutTimeLenSpeak)/60.00 AS DECIMAL(18,2)) as CalloutTimeLenSpeak, " +
                "sum(CountSatisAgentNo) as CountSatisAgentNo,\n" +
                "sum(CountSatisVery) as CountSatisVery, " +
                "sum(CountSatisGeneral) as CountSatisGeneral, " +
                "sum(CountSatisBad) as CountSatisBad, " +
                "sum(CountSatisCustNo) as CountSatisCustNo, " +
                "concat(min(DATE_FORMAT(dt,'%Y-%m-%d') ) ,' 到 ',max(DATE_FORMAT(dt,'%Y-%m-%d') ) )as dt " +
                "FROM dddReport_Work where 1=1 "+strSQL+
                " group by GroupName ) as a ";//数据表
        int count1 = currentNum*(currentPage-1);
        sql = "SELECT  * FROM "+DataTable+
                "WHERE 1=1  ORDER BY id DESC limit "+count1+","+currentNum;
        System.out.println("companySQL="+sql);

        QueryTableJson jsonData = new QueryTableJson();//创建json对象
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
                        data.add(os.getString("GroupName"));//1工作组
                        data.add(os.getString("CountCallIn"));//2来电总量=呼入总量
                        data.add(os.getString("CallIn"));//3接听总量=呼入总量-呼入呼损
                        data.add(os.getString("CountCallOut"));//4外拨量
                        data.add(os.getString("CountCallOutFail"));//5呼损量=呼出呼损
                        data.add(os.getString("CallinTimeLenSpeak"));//6来电总时长
                        data.add(os.getString("TimeLenBusy"));//7置忙时长
                        data.add(os.getString("TimeLenIdle"));//8空闲时长
                        data.add(os.getString("CallinTimeLenRing"));//9振铃时长
                        data.add(os.getString("CalloutTimeLenSpeak"));//10外拨时长
                        data.add(os.getString("CallinTimeLenAcd"));//11排队时长
                        data.add(os.getString("CountCallInAcd"));//12排队个数
//                        int acdAverageTime = os.getInt("CountCallInAcd")>0?(os.getInt("CallinTimeLenAcd")/os.getInt("CountCallInAcd")):os.getInt("CallinTimeLenAcd");
//                        data.add(String.valueOf(acdAverageTime));//13平均排队时长
                        data.add(os.getString("acdAverageTime"));//13平均排队时长
                        data.add(os.getString("CountSatisAgentNo"));//14-坐席未请求
                        data.add(os.getString("CountSatisVery"));//15-非常满意
                        data.add(os.getString("CountSatisGeneral"));//16-一般
                        data.add(os.getString("CountSatisBad"));//17-不满意
                        data.add(os.getString("CountSatisCustNo"));//18-坐席未评价
                        data.add(os.getString("CountCallOutEffective"));//19-有效外拨
                        listData_1.add(data);
                    }
                    jsonData.setDataList(listData_1);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setDataType("Y");
                    /*添加导出excel语句到相应的session*/
                    HttpSession session = request.getSession();
                    Query query = new Query();
                    session.setAttribute("reportGroup" + "ToExcel", query.setExcelSql(DataTable, "", "id"));
                    jsonData.setTableName("reportGroup");
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
                String[] listTotal = {"countCallIn","callIn","countCallOut","countCallOutFail","callinTimeLenSpeak","timeLenBusy","timeLenIdle",
                        "callinTimeLenRing","calloutTimeLenSpeak","callinTimeLenAcd","countCallInAcd","acdAverageTime","countSatisAgentNo",
                        "countSatisVery","countSatisGeneral","countSatisBad","countSatisCustNo","countCallOutEffective"};
                commonReport cr = new commonReport();
                String sqlTotal = cr.getSql(listTotal, DataTable);
                System.out.println("查询工作组报表统计数据：\n"+sqlTotal);
                jsonData.setTotalList(cr.sqlListData(new GroupBean(), sqlTotal, listTotal));
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

