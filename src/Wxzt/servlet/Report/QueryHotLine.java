package Wxzt.servlet.Report;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Report.javabean.HotLineBean;
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

public class QueryHotLine extends HttpServlet {
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

        String strSQL = "";//查询条件
        String countSQL;//查询数据语句
        String sql ;//查询语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet
        String DataTable;//数据表

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        //昨日日期
        Date d = new Date(System.currentTimeMillis()-1000*60*60*24);
        String Yesterday = formatter.format(d);
        //今日日期
        Date d_1 = new Date();
        String todayTime = formatter.format(d_1);
        //获取jQuery传递的参数
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        //无效数据,当参数值等于2时，不显示无效数据
        String noData = request.getParameter("noData");
        if(noData.equals("2")){
            strSQL += " and HotLine!='--' ";
        }
        //查询线路
        String HotLine = request.getParameter("HotLine");
        if(HotLine.equals("1")){//线路1
            strSQL += " and (HotLine='89041888' or HotLine='89041868' or HotLine='89041800' or HotLine='1089041888' or HotLine='1089041868' or HotLine='1089041800') ";
        }else if(HotLine.equals("2")){//线路2
            strSQL += " and (HotLine='67871034') ";
        }else if(HotLine.equals("3")){//线路2
            strSQL += " and (HotLine='分机互拨') ";
        }
        String firstDT = request.getParameter("firstDt");//查询开始时间 min
        String endDT = request.getParameter("endDt");//查询开始时间 max
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

        DataTable = "(SELECT max(id) as id,HotLine,sum(CountCallIn) as CountCallIn," +
                " concat (max(DATE_FORMAT(dt,'%Y-%m-%d') ),' 到 ', min(DATE_FORMAT(dt,'%Y-%m-%d') )) as DT, " +
                "sum(CountCallInFail) as CountCallInFail," +
                "sum(CountCallOut) as CountCallOut," +
                "sum(CountCallOutFail) as CountCallOutFail, " +
                "sum(CountCallInFailDtmf) as CountCallInFailDtmf," +
                "sum(CountDTMF0) as CountDTMF0," +
                "sum(CountDTMF1) as CountDTMF1, " +
                "sum(CountDTMF2) as CountDTMF2," +
                "sum(CountDTMF3) as CountDTMF3," +
                "sum(CountDTMF4) as CountDTMF4, " +
                "sum(CountDTMF5) as CountDTMF5," +
                "sum(CountDTMF6) as CountDTMF6," +
                "sum(CountDTMF7) as CountDTMF7, " +
                "sum(CountDTMF8) as CountDTMF8," +
                "sum(CountDTMF9) as CountDTMF9, " +
                "sum(CountDTMFXing) as CountDTMFXing," +
                "sum(CountDTMFJing) as CountDTMFJing," +
                "sum(CountDTMFNo) as CountDTMFNo " +
                "FROM dddReport_HotLine where 1=1  "+strSQL+" "+
                "group by HotLine ) as c ";//数据表
        int count1 = currentNum*(currentPage-1);
        sql = "SELECT  * FROM "+DataTable+
                " WHERE 1=1 ORDER BY ID DESC limit "+count1+","+currentNum;
        countSQL = "select count(*) from "+DataTable;
        System.out.println("SQLstate="+sql);

        //查询表单数据数量
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
                        data.add(os.getString("DT"));//0日期
                        data.add(os.getString("HotLine"));//1线路
                        data.add(os.getString("CountCallIn"));//2呼入数量
                        data.add(os.getString("CountCallInFail"));//3呼入损失
                        data.add(os.getString("CountCallOut"));//4呼出数量
                        data.add(os.getString("CountCallOutFail"));//5呼出损失数量
                        data.add(os.getString("CountCallInFailDtmf"));//6-呼入按钮呼损
                        data.add(os.getString("CountDTMF0"));//7-按0数
                        data.add(os.getString("CountDTMF1"));//8-按1数
                        data.add(os.getString("CountDTMF2"));//9-按2数
                        data.add(os.getString("CountDTMF3"));//10-按3数
                        data.add(os.getString("CountDTMF4"));//11-按4数
                        data.add(os.getString("CountDTMF5"));//12-按5数
                        data.add(os.getString("CountDTMF6"));//13-按6数
                        data.add(os.getString("CountDTMF7"));//14-按7数
                        data.add(os.getString("CountDTMF8"));//15-按8数
                        data.add(os.getString("CountDTMF9"));//16-按9数
                        data.add(os.getString("CountDTMFXing"));//17-按*数
                        data.add(os.getString("CountDTMFJing"));//18-按#数
                        data.add(os.getString("CountDTMFNo"));//19-无按键
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
                    session.setAttribute("reportHotLine" + "ToExcel", query.setExcelSql(DataTable, "", "id"));
                    jsonData.setTableName("reportHotLine");
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
                String[] listTotal = {"countCallIn","countCallInFail","countCallOut","countCallOutFail","countCallInFailDtmf","countDTMF0","countDTMF1",
                        "countDTMF2","countDTMF3","countDTMF4","countDTMF5","countDTMF6","countDTMF7",
                        "countDTMF8","countDTMF9","countDTMFXing","countDTMFJing","countDTMFNo"};
                commonReport cr = new commonReport();
                String sqlTotal = cr.getSql(listTotal, DataTable);
                System.out.println("查询线路报表统计数据：\n"+sqlTotal);
                jsonData.setTotalList(cr.sqlListData(new HotLineBean(), sqlTotal, listTotal));
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

