package Wxzt.servlet.QueryReport;

import Wxzt.servlet.Common.Query;
import Wxzt.tool.JDBC;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/2/4.
 */
public class ReportSql extends Query{
    //各线路当日呼入数据的已接通和未接通数量占比
    public ArrayList<HotLineBean> getConnectCount(HttpServletRequest request){
        ArrayList<HotLineBean> list = new ArrayList<HotLineBean>();
        String firstTime = request.getParameter("firstTime");
        String endTime = request.getParameter("endTime");
        String hotlines = request.getParameter("hotline");
        String[] hotline = hotlines.split(",");
        String sql = "";
        String sql2 = "";
        String hotlineStr = "(";
        int callinsum = 0;
        int callinfailsum = 0;
        HotLineBean hotLineBean = new HotLineBean();
        HotLineBean hotLineBean2 = new HotLineBean();
        hotLineBean.setName("已接通");
        hotLineBean2.setName("未接通");
        for (int i = 0; i < hotline.length; i++) {
            if (i != hotline.length - 1) {
                hotlineStr += "'" + hotline[i] + "',";
            } else {
                hotlineStr += "'" + hotline[i] + "' )";
            }
        }
        sql = "select sum(countcallin),sum(CountCallInFail) from dddReport_Hotline where hotline in "+ hotlineStr;
        if(firstTime!=null&&!firstTime.equals("")){
            sql += " and dt >= '"+firstTime+"'";
        }
        if(endTime!=null&&!endTime.equals("")){
            sql += " and dt < '"+endTime+"'";
        }

       if((firstTime==null||firstTime.equals(""))&&(endTime==null||endTime.equals(""))){
            sql = "select sum(countcallin),sum(CountCallInFail) from dddReport_Hotline where hotline in" + hotlineStr;
        }
        System.out.println("=======" + sql);
        try {
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while (rs.next()) {
                String countcallins = rs.getString(1);
                String CountCallInFails = rs.getString(2);
                if(countcallins==null|| Objects.equals(countcallins, "")){
                    countcallins = "0";
                }
                callinsum = Integer.parseInt(countcallins);
                if(CountCallInFails==null|| Objects.equals(CountCallInFails, "")){
                    CountCallInFails = "0";
                }
                callinfailsum = Integer.parseInt(CountCallInFails);
            }
            jdbc.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        hotLineBean.setValue(callinsum);
        hotLineBean2.setValue(callinfailsum);
        list.add(hotLineBean);
        list.add(hotLineBean2);
        return list;
    }
    //线路每小时呼叫数量走势图
    public List<ReportBean> getHourCount(HttpServletRequest request) {
        List<ReportBean> list = new ArrayList<>();
        ArrayList<Object> list1 = new ArrayList<>();
        ReportBean reportBean = new ReportBean();
        reportBean.setName("每小时接听数量");
        String sipnum = request.getParameter("sipnum");
        String firstTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        firstTime = sdf.format(new Date());
        String hourStr = "(";
        int hour[] = new int[24];
        for(int i = 0; i < 24;i++){
            if (i != 23) {
                hourStr += i+ ",";
            } else {
                hourStr += i + " )";
            }
        }
        String sql = "";
        //每小时呼出数量
        String isMgr = request.getParameter("isMgr");
        if("1".equals(isMgr)){
            //个人
            sql = "select hour(dtcreate) ,count(*) from dddcdr where sipnum = '"+sipnum+"'";

            sql += "  and  DATE_FORMAT(dtcreate,'%Y-%c-%d')= '"+firstTime+"'";

            sql += " and direction = 'in' and hour(dtcreate) in " + hourStr + " group by hour(dtcreate)";
        }else if("2".equals(isMgr)){
            //组别
            String groupid = request.getParameter("groupid");
            String sql2 = "select distinct sipnum from dddcdr where groupid = "+groupid;
            String sipnumList = getSipnumByGroupid(sql2);
            sql = "select hour(dtcreate) ,count(*) from dddcdr where sipnum in "+sipnumList+"";

            sql += "  and  DATE_FORMAT(dtcreate,'%Y-%c-%d')= '"+firstTime+"'";

            sql += " and direction = 'in' and hour(dtcreate) in " + hourStr + " group by hour(dtcreate)";
        }else if("3".equals(isMgr)){
            //全部
            sql = "select hour(dtcreate) ,count(*) from dddcdr where 1=1 ";

            sql += "  and  DATE_FORMAT(dtcreate,'%Y-%c-%d')= '"+firstTime+"'";

            sql += " and direction = 'in' and hour(dtcreate) in " + hourStr + " group by hour(dtcreate)";
        }
        System.out.print("=====" + sql);
        try {
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while (rs.next()){
                String times = rs.getString(1);
                if(times==null|| Objects.equals(times, "")){
                    times = "0";
                }
                int time = Integer.parseInt(times);
                String counts = rs.getString(2);
                if(counts==null|| Objects.equals(counts, "")){
                    counts = "0";
                }
                Integer count = Integer.parseInt(counts);
                hour[time] = count;
            }
            jdbc.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //遍历hour
        for(int i = 0;i<24;i++){
            list1.add(hour[i]);
        }
        reportBean.setData(list1);
        reportBean.setType("line");
        list.add(reportBean);
        return list;
    }
    /*当日外拨量*/
    public ArrayList<HotLineBean> getCountCallout(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format.format(new Date());
        String sql = "select sum(countCallout) from dddReport_Hour where dt>'"+newTime+"'";
        String count = sqlOneData(sql);
        if(count==null) count = "0";
        HotLineBean h = new HotLineBean();
        h.setValue(Integer.parseInt(count));
        ArrayList<HotLineBean> list = new ArrayList<HotLineBean>();
        list.add(h);
        return list;
    }

    //各线路,组别,坐席 每小时呼叫数量走势图
    public List<ReportBean> getHourCount_new(HttpServletRequest request, int flag){
        List<ReportBean> list = new ArrayList<>();
        ArrayList<Object> list1 = new ArrayList<>();
        ReportBean reportBean = new ReportBean();
        reportBean.setName("每小时呼叫数量");
        String hotlines = request.getParameter("hotline");
        String[] hotline = null;
        if(hotlines!=null&&!hotlines.equals("")){
            hotline = hotlines.split(",");
        }

        String firstTime = request.getParameter("firstTime");
        String endTime = request.getParameter("endTime");
        String groupNums = request.getParameter("groupNum");
        String[] groupNum = null;
        if(groupNums!=null&&!groupNums.equals("")){
            groupNum = groupNums.split(",");
        }

        String workNums = request.getParameter("workNum");
        String[] workNum = null;
        if(workNums!=null&&!workNums.equals("")){
            workNum = workNums.split(",");
        }
        String hotlineStr = "(";
        String hourStr = "(";
        String groupNumStr = "(";
        String workNumStr = "(";
        int hour[] = new int[24];
        if(hotlines.length()!=0){
            for (int i = 0; i < hotline.length; i++) {
                if (i != hotline.length - 1) {
                    hotlineStr += "'" + hotline[i] + "',";
                } else {
                    hotlineStr += "'" + hotline[i] + "' )";
                }
            }
        }else if(hotlines.length()==0){
            hotlineStr += "'vsionwxztabccode')";
        }
        if(groupNums.length()!=0){
            for (int i = 0; i < groupNum.length; i++) {
                if (i != groupNum.length - 1) {
                    groupNumStr += "'" + groupNum[i] + "',";
                } else {
                    groupNumStr += "'" + groupNum[i] + "' )";
                }
            }
        }else if(groupNums.length()==0){
            groupNumStr += "1008610010110112)";
        }
        if(workNums.length()!=0){
            for (int i = 0; i < workNum.length; i++) {
                if (i != workNum.length - 1) {
                    workNumStr += "'" + workNum[i] + "',";
                } else {
                    workNumStr += "'" + workNum[i] + "' )";
                }
            }
        }else if(workNums.length()==0){
            workNumStr += "'vsionwxztabccode')";
        }

        for(int i = 0; i < 24;i++){
            if (i != 23) {
                hourStr += i+ ",";
            } else {
                hourStr += i + " )";
            }
        }
        String sql = "";
        //每小时呼入数量
        if (flag == 1){
            sql = "select hour(dtstart) ,count(*) from dddcdr where hotline in "  + hotlineStr;
            if(firstTime!=null&!firstTime.equals("")){
                sql += "  and  dtstart > '" + firstTime+"'";
            }
            if(endTime!=null&!endTime.equals("")){
                sql += "  and  dtstart < '" + endTime+"'";
            }
            sql += " and worknum in "+workNumStr+" and groupnum in "+groupNumStr;
            sql += "and direction = 'in' and hour(dtstart) in " + hourStr + " group by hour(dtstart)";
           /* if((firstTime==null||firstTime.equals(""))&&(endTime==null||endTime.equals(""))){
                sql = "select dateName(hh,dtstart) ,count(*) from dddcdr where hotline in  " + hotlineStr + " and direction = 'out' and dateName(hh,dtstart) in " + hourStr + " group by dateName(hh,dtstart)";
            }*/
        }else if(flag == 2){
            //每小时呼出
            sql = "select hour(dtstart) ,count(*) from dddcdr where hotline in "  + hotlineStr;
            if(firstTime!=null&!firstTime.equals("")){
                sql += "  and  dtstart > '" + firstTime+"'";
            }
            if(endTime!=null&!endTime.equals("")){
                sql += "  and  dtstart < '" + endTime+"'";
            }
            sql += " and worknum in "+workNumStr+" and groupnum in "+groupNumStr;
            sql += "and direction = 'out' and hour(dtstart) in " + hourStr + " group by hour(dtstart)";
           /* if((firstTime==null||firstTime.equals(""))&&(endTime==null||endTime.equals(""))){
                sql = "select dateName(hh,dtstart) ,count(*) from dddcdr where hotline in  " + hotlineStr + " and direction = 'in' and dateName(hh,dtstart) in " + hourStr + " group by dateName(hh,dtstart)";
            }*/
        }
        System.out.print("=====" + sql);
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while (rs.next()){
                String times = rs.getString(1);
                if(times==null|| Objects.equals(times, "")){
                    times = "0";
                }
                int time = Integer.parseInt(times);
                String counts = rs.getString(2);
                if(counts==null|| Objects.equals(counts, "")){
                    counts = "0";
                }
                Integer count = Integer.parseInt(counts);
                hour[time] = count;
            }
            jdbc.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //遍历hour
        for(int i = 0;i<24;i++){
            list1.add(hour[i]);
        }
        reportBean.setData(list1);
        reportBean.setType("line");
        list.add(reportBean);
        return list;
    }
    //今日个人话务总量
    public ArrayList<PersonTelBean> getPersonTel(HttpServletRequest request){
        ArrayList<PersonTelBean> list = new ArrayList<>();
        PersonTelBean bean = new PersonTelBean();
        String isMgr = request.getParameter("isMgr");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String time = sdf.format(new Date());
        String sql = "";
        //个人
        if("1".equals(isMgr)){
            String worknum = request.getParameter("worknum");
            sql = " select count(*),direction from dddcdr where date_format(dtcreate, '%Y-%c-%d') = '"+time+"' and worknum = '"+worknum+"' group by direction";
        }else if("2".equals(isMgr)){
            //组别
            String groupid = request.getParameter("groupid");
            String sql2 = "select distinct worknum from dddwork where groupid ="+groupid;
            //获取此groupid下的所有的坐席的号码
            String worknunList = getWorknumByGroupid(sql2);
            sql = " select count(*),direction from dddcdr where date_format(dtcreate, '%Y-%c-%d') = '"+time+"' and worknum in "+worknunList+" group by direction";
        }else if("3".equals(isMgr)){
            sql = " select count(*),direction from dddcdr where date_format(dtcreate, '%Y-%c-%d') = '"+time+"' group by direction";
        }
        JDBC jdbc = null;
        ResultSet rs = null;
        try{
            jdbc = new JDBC();
            System.out.println("执行的sql"+sql);
            rs = jdbc.executeQuery(sql);
            int huru = 0;
            int huchu = 0;
            int total = 0;
            while(rs.next()){
                String direction = rs.getString("direction");
                if("in".equals(direction)){
                            //呼入量
                    huru = rs.getInt(1);
                }else if("out".equals(direction)){
                    //呼出量
                    huchu = rs.getInt(1);
                }
            }
            jdbc.closeConnection();
            total = huru+huchu;
            bean.setHuchu(huchu);
            bean.setHuru(huru);
            bean.setTotal(total);
            list.add(bean);
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //首页上面满意度
    public ArrayList<SatisBean> Manyidu(HttpServletRequest request){
        ArrayList<SatisBean> list = new ArrayList<>();
        SatisBean bean = new SatisBean();
        String sql = "";
        String isMgr = request.getParameter("isMgr");
        if("1".equals(isMgr)){
            //个人
            String worknum = request.getParameter("worknum");
            sql = "select count(*),scoreSatis from dddcdr where worknum = '"+worknum+"' group by scoreSatis ";
        }else if("2".equals(isMgr)){
            //组别
            String groupid = request.getParameter("groupid");
            String sql2 = "select distinct worknum from dddwork where groupid = "+groupid;
            String worknumList = getWorknumByGroupid(sql2);
            System.out.println("worknumList"+worknumList);
            sql = "select count(*),scoreSatis from dddcdr where worknum in "+worknumList+" group by scoreSatis ";
        }else if("3".equals(isMgr)){
            //全部
            sql = "select count(*),scoreSatis from dddcdr where 1=1 group by scoreSatis ";
        }
        try {
            JDBC jdbc = new JDBC();
            System.out.println("执行的语句"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            double manyidu = 0;
            int weipingjia = 0;
            int manyi = 0;
            int yiban = 0;
            int bumanyi = 0;
            while(rs.next()){
                int scoreSatis = rs.getInt("scoreSatis");
                int count = rs.getInt("count(*)");
                if(scoreSatis == 1){
                    //满意
                    manyi += count;
                }else if(scoreSatis == 2){
                    //一般
                    yiban += count;
                }else if(scoreSatis == 3){
                    //不满意
                    bumanyi += count;
                }else if(scoreSatis == 4){
                    //未评价
                    weipingjia += count;
                }
            }
            jdbc.closeConnection();
            double total = weipingjia+manyi+yiban+bumanyi;
            if(total == 0){
                //没有一条数据的话 满意度的默认值是0
                manyidu = 0;
            }else{
                manyidu = (1-(bumanyi/total))*100;
            }
            DecimalFormat df = new DecimalFormat("#.0");
            String format = df.format(manyidu);
            manyidu = Double.parseDouble(format);
            bean.setBumanyi(bumanyi);
            bean.setManyi(manyi);
            bean.setManyidu(manyidu);
            bean.setWeipingjia(weipingjia);
            bean.setYiban(yiban);
            list.add(bean);
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //今日上线总时长
    public ArrayList<OnLineBean> getOnLineInfo(HttpServletRequest request){
        String sql = "";
        ArrayList<OnLineBean> list = new ArrayList<>();
        OnLineBean bean = new OnLineBean();
        String isMgr = request.getParameter("isMgr");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String time = sdf.format(new Date());
        if("1".equals(isMgr)){
            //个人
            String worknum = request.getParameter("worknum");
            sql = "select count(*) as count,state,sum(timelen) as timelen from dddstate where worknum = '"+worknum+"'  and  DATE_FORMAT(dtstart,'%Y-%c-%d') = '"+time+"' GROUP BY state";
        }else if("2".equals(isMgr)){
            //组别
            String groupid = request.getParameter("groupid");
            String sql2 = "select distinct worknum from dddwork where groupid = "+groupid;
            String worknumList = getWorknumByGroupid(sql2);
            sql = "select count(*) as count,state,sum(timelen) as timelen from dddstate where worknum in "+worknumList+"  and  DATE_FORMAT(dtstart,'%Y-%c-%d') = '"+time+"' GROUP BY state";

        }else if("3".equals(isMgr)){
            sql = "select count(*) as count,state,sum(timelen) as timelen from dddstate where 1=1  and  DATE_FORMAT(dtstart,'%Y-%c-%d') = '"+time+"' GROUP BY state";
        }
        try {
            JDBC jdbc = new JDBC();
            System.out.println("执行语句"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            int qianru = 0,qianchu = 0,eat = 0,rest = 0,total = 0;
            while (rs.next()){
                String state = rs.getString("state");
                int count = rs.getInt("count");
                int timelen = rs.getInt("timelen");
                if("logout".equals(state)){
                    // 签出
                    qianchu += count;
                }else if("login".equals(state)){
                    //嵌入
                    qianru += count;
                    total += timelen;
                }else if("eat".equals(state)){
                    //吃饭
                    eat += timelen;
                    total += timelen;
                }else if("rest".equals(state)){
                    //休息
                    rest += timelen;
                    total += timelen;
                }else{
                    total += timelen;
                }
            }
            if(eat%60==0){
                bean.setEatTotal(eat/60);
            }else{
                bean.setEatTotal(eat/60+1);
            }
            bean.setQianchu(qianchu);
            bean.setQianru(qianru);
            if(rest%60==0){
                bean.setXiaoxiuTotal(rest/60);
            }else{
                bean.setXiaoxiuTotal(rest/60+1);
            }
            if (total%60==0){
                bean.setOnLineTotal(total/60);
            }else {
                bean.setOnLineTotal(total/60+1);
            }
            list.add(bean);
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //今日个人平均通话时长
    public ArrayList<PersonAverBean> getPersonAver(HttpServletRequest request){
        ArrayList<PersonAverBean> list = new ArrayList<>();
        PersonAverBean bean = new PersonAverBean();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String time = sdf.format(new Date());
        String sql = "select worknum,timelenspeak from dddcdr where  DATE_FORMAT(dtcreate,'%Y-%c-%d') = '"+time+"' group by worknum";
        try{
            JDBC jdbc = new JDBC();
            System.out.println("执行语句"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            int m = 0;//记录总人数
            int n = 0;//记录总通话时长
            String str = "";
            while(rs.next()){
                String worknum = rs.getString("worknum");
                int timelenspeak = rs.getInt("timelenspeak");
                if(!str.equals(worknum)){
                    m += 1;//人数增加1
                }
                n += timelenspeak;
                str = worknum;
            }
            int m1 = 0;
            if(m!=0){
                if(n%m==0){
                    m1 = n/m;
                }else {
                    m1 = n/m+1;
                }
            }else if(m==0){
                m1 =  0;
            }
            bean.setTimelen(m1);
            list.add(bean);
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //权限接通率 满意度(双折线图)
    public ArrayList<DoubleLineBean> getDoubleLine(HttpServletRequest request){
        ArrayList<DoubleLineBean> list = new ArrayList<>();
        DoubleLineBean bean = new DoubleLineBean();
        //向前台传递时间
        ArrayList list1 = getDateList();
        //获取全线接通率
        double[] doublelist2 = getJTList();
        ArrayList list2 = new ArrayList();
        for(int i=0;i<doublelist2.length;i++){
            list2.add(doublelist2[i]);
        }
        //双折线图中满意的list
        double[] doublelist3 = getSatisList();
        ArrayList list3 = new ArrayList();
        for(int i=0;i<doublelist3.length;i++){
            list3.add(doublelist3[i]);
        }
        bean.setList1(list1);
        bean.setList2(list2);
        bean.setList3(list3);
        list.add(bean);
        return list;
    }
    //双折线图中满意度的list
    public double[] getSatisList(){
        String[] timeList = getDateListSql2();
        String timeList2 = getDateListSql();
        ArrayList list2 = getDateList();
        double[] list = {0, 0, 0, 0, 0, 0, 0};
        String sql = " select ScoreSatis as score ,count(*) as count ,DATE_FORMAT(dtcreate,'%Y-%c-%d') as dtcreate from dddcdr where DATE_FORMAT(dtcreate , '%Y-%c-%d') " +
                "in "+timeList2+" group by dtcreate,score order by dtcreate desc ";
        try{
            JDBC jdbc = new JDBC();
            System.out.println("getSatisList"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            int manyi = 0,total = 0;
            String timestr = "";
            while(rs.next()){
                String score = rs.getString("score");
                int count = rs.getInt("count");
                String dtcreate = rs.getString("dtcreate");
                if(!timestr.equals(dtcreate)){
                    //不是上一个日期
                    Double rate = 0.0;
                    if(total!=0){
                         rate = (manyi/(total*1.0))*100;
                        System.out.println("rate"+rate+"*****");
                    }
                    DecimalFormat df = new DecimalFormat("#.0");
                    String format = df.format(rate);
                    double parseDouble = Double.parseDouble(format);
                    int index = -1;
                    for(int i=0;i<timeList.length;i++){
                        if(dtcreate.equals(timeList[i])){
                            index = i;
                        }
                    }
                    if(index!=-1){
                        list[index] = parseDouble;
                    }
                    manyi = 0;
                    total = 0;
                }
                if("1".equals(score)){
                    //满意
                    manyi += count;
                    total += count;
                }else{
                    total += count;
                }
                timestr = dtcreate;
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //全线接通率
    public double[] getJTList(){
        String[] timeList = getDateListSql2();
        String timeList2 = getDateListSql();
        ArrayList list2 = getDateList();
        double[] list = {0, 0, 0, 0, 0, 0, 0};
        String sql = "select sum(countcallin) as countcallin,sum(countcallinfail) as countcallinfail,sum(countcallout) as countcallout,sum(countcalloutfail) as countcalloutfail,DATE_FORMAT(dt,'%Y-%c-%d') as dt " +
                " from dddreport_hotline where dt in "+timeList2+" group by dt order by dt desc";
        try{
            JDBC jdbc = new JDBC();
            System.out.println("全线接通率:"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                String dt = rs.getString("dt");
                int countcallin = rs.getInt("countcallin");
                int countcallinfail = rs.getInt("countcallinfail");
                int countcallout = rs.getInt("countcallout");
                int countcalloutfail = rs.getInt("countcalloutfail");
                double rate = ((1.0*countcallin+countcallout)/(countcallin+countcallinfail+countcallout+countcalloutfail))*100;
                DecimalFormat df = new DecimalFormat("#.0");
                String format = df.format(rate);
                double parseDouble = Double.parseDouble(format);
                int index = -1;
                for(int i = 0;i<timeList.length;i++){
                    if(dt.equals(timeList[i])){
                        index = i;
                    }
                }
                if(index!=-1){
                    list[index] = parseDouble;
                }
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //生成时间字符串,用于查询语句sql中
    public String getDateListSql(){
        String str = "('";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String time1 = sdf.format(new Date());
        String time2 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));//前一天
        String time3 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*2)));//前2天
        String time4 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*3)));//前3天
        String time5 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*4)));//前4天
        String time6 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*5)));//前5天
        String time7 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*6)));//前6天
        str+=time1+"','"+time2+"','"+time3+"','"+time4+"','"+time5+"','"+time6+"','"+time7+"')";
        return str;
    }
    //生成时间字符串,用于查询语句sql中
    public String[] getDateListSql2(){
        String str[] = new String[7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String time1 = sdf.format(new Date());
        String time2 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));//前一天
        String time3 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*2)));//前2天
        String time4 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*3)));//前3天
        String time5 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*4)));//前4天
        String time6 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*5)));//前5天
        String time7 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*6)));//前6天
        str[0] = time7;
        str[1] = time6;
        str[2] = time5;
        str[3] = time4;
        str[4] = time3;
        str[5] = time2;
        str[6] = time1;
        return str;
    }
    //用于前台时间的展示
    public ArrayList getDateList(){
        ArrayList list = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        String time1 = sdf.format(new Date());
        String time2 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));//前一天
        String time3 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*2)));//前2天
        String time4 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*3)));//前3天
        String time5 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*4)));//前4天
        String time6 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*5)));//前5天
        String time7 = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24*6)));//前6天
        list.add(time7);
        list.add(time6);
        list.add(time5);
        list.add(time4);
        list.add(time3);
        list.add(time2);
        list.add(time1);
        return list;
    }
    //柱状图(昨日各线路对比)
  public ArrayList<YesterdayHotlineBean> getYesterdayHotline(HttpServletRequest request){
    ArrayList<YesterdayHotlineBean> list = new ArrayList<>();
    YesterdayHotlineBean bean = new YesterdayHotlineBean();
    //获取各个热线(X轴坐标)
      ArrayList<String> list1 = getHotlineList();
      //获取送达量
      ArrayList list2 = new ArrayList();
      int[] intlist2 = getSongdaTotal();
      for(int i=0;i<intlist2.length;i++){
          list2.add(i,intlist2[i]);
      }
      ArrayList list3 = new ArrayList();
      //获取来话量
      int[] intlist3 = getLaiHuaTotal();
      for(int i=0;i<intlist3.length;i++){
          list3.add(i,intlist3[i]);
      }
      //获取接通量
      ArrayList list4 = new ArrayList();
      int[] intlist4 = getJieTongTotal();
      for(int i=0;i<intlist4.length;i++){
          list4.add(i,intlist4[i]);
      }
      bean.setList1(list1);
      bean.setList2(list2);
      bean.setList3(list3);
      bean.setList4(list4);
      list.add(bean);
      return list;
}
    //获取接通量
    public int[] getJieTongTotal(){
        String hotlineList = getHotlineListSQL();
        ArrayList<String> hotlineList1 = getHotlineList();
        int[] list = new int[hotlineList1.size()];
        for(int i = 0;i<hotlineList1.size();i++){
            list[i] = 0;
        }
        //获取昨天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String yesterDay = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));
        String sql = " select count(*),hotline from dddcdr where DATE_FORMAT(dtcreate,'%Y-%c-%d') = '"+yesterDay+"'  and hotline in "+hotlineList+" and direction = 'in' and timelenspeak!=0" +
                " order by hotline desc ";
        try{
            JDBC jdbc = new JDBC();
            System.out.println("获取接通量"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                //获取送达量
                int i = rs.getInt(1);
                String hotline = rs.getString("hotline");
                int index = hotlineList1.indexOf(hotline);
                if(index!=-1){
                   list[index] = i;
                }

            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    //获取来话量
    public int[] getLaiHuaTotal(){
        String hotlineList = getHotlineListSQL();
        ArrayList<String> hotlineList1 = getHotlineList();
        int[] list = new int[hotlineList1.size()];
        for(int i=0;i<hotlineList1.size();i++){
            list[i] = 0;
        }
        //获取昨天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String yesterDay = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));
        String sql = " select count(*),hotline from dddcdr where DATE_FORMAT(dtcreate,'%Y-%c-%d') = '"+yesterDay+"'  and hotline in "+hotlineList+" and direction = 'in' " +
                " order by hotline desc ";
        try{
            JDBC jdbc = new JDBC();
            System.out.println("来话量"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                //获取送达量
                int i = rs.getInt(1);
                String hotline = rs.getString("hotline");
                int index = hotlineList1.indexOf(hotline);
                if(index!=-1){
                    list[index] = i;
                }
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //获取热线的送达量
    public int[] getSongdaTotal(){
        ArrayList<String> hotlineList1 = getHotlineList();
        int length = hotlineList1.size();
        int[] list = new int[length];
        for(int i=0;i<hotlineList1.size();i++){
            list[i] = 0;
        }
        String hotlineList = getHotlineListSQL();
        //获取昨天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String yesterDay = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));
        String sql = " select count(*),hotline from dddcdr where DATE_FORMAT(dtcreate,'%Y-%c-%d') = '"+yesterDay+"'  and hotline in "+hotlineList+" and (direction = 'in' and timelenspeak!=0 or timelenspeak = 0 and timelenacd !=0 )" +
                "  order by hotline desc";
        try{
            JDBC jdbc = new JDBC();
            System.out.println("送达量"+sql);
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                //获取送达量
                int i = rs.getInt(1);
                String hotline = rs.getString("hotline");
                int index = hotlineList1.indexOf(hotline);
                if(index!=-1){
                    list[index] = i;
                }
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //获取热线号码的集合String 用于sql语句中
    public String getHotlineListSQL(){
      String hotlineStr = "('";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String yesterDay = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));

        String sql  = " select distinct hotline from dddcdr where 1=1  and DATE_FORMAT(dtcreate,'%Y-%c-%d') = '"+yesterDay+"' and direction = 'in' order by hotline desc";
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            if(!rs.next()){
                hotlineStr += " ')";
            }
            while(rs.next()){
                String hotline = rs.getString("hotline");
                if(rs.isLast()){
                    hotlineStr += hotline+"')";
                }else{
                    hotlineStr += hotline+"','";
                }
            }
            jdbc.closeConnection();

        }catch (Exception e){
            e.printStackTrace();
        }
        if("('".equals(hotlineStr)){
            hotlineStr += "')";
        }
        return hotlineStr;
    }
    //柱状图中获取X坐标(热线号码)
    public ArrayList<String> getHotlineList(){
        ArrayList list = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String yesterDay = sdf.format(new Date((System.currentTimeMillis() - 1000 * 60 * 60 * 24)));
        String sql  = " select distinct hotline from dddcdr where 1=1  and DATE_FORMAT(dtcreate,'%Y-%c-%d') = '"+yesterDay+"' and direction = 'in' order by hotline desc";
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                String hotline = rs.getString("hotline");
                list.add(hotline);
            }
            jdbc.closeConnection();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //操作日志
    //获取传过来的groupid下的worknum
    public String getWorknumByGroupid(String sql){
        String worknumStr = "('";
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            if(!rs.next()){
                worknumStr += "')";
            }
            while (rs.next()){
                if(!rs.isLast()){
                    String worknum = rs.getString("worknum");
                    worknumStr += worknum+"','";
                }else if(rs.isLast()){
                    String worknum = rs.getString("worknum");
                    worknumStr += worknum+"')";
                }
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return worknumStr;
    }
    //获取传过来的groupid下的sipnum
    public String getSipnumByGroupid(String sql){
        String worknumStr = "('";
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            if(!rs.next()){
                worknumStr += "')";
            }
            while (rs.next()){
                if(!rs.isLast()){
                    String worknum = rs.getString("sipnum");
                    worknumStr += worknum+"','";
                }else if(rs.isLast()){
                    String worknum = rs.getString("sipnum");
                    worknumStr += worknum+"')";
                }
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return worknumStr;
    }
    //操作日志
    public ArrayList<OperateBean> getOpera(HttpServletRequest request){
        String worknum = request.getParameter("worknum");
        ArrayList<OperateBean> list = new ArrayList<>();
        OperateBean bean1 = new OperateBean();
        OperateBean bean2 = new OperateBean();
        OperateBean bean3 = new OperateBean();
        OperateBean bean4 = new OperateBean();
        String sql = "select state, dtstart from dddstate where worknum = '"+worknum+"' order by dtstart desc limit 0,4";
        try{
            int i = 1;
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                String state = rs.getString("state");
                String dtstart = rs.getString("dtstart");
                if(i==1){
                    if("idle".equals(state)){
                        bean1.setState("示闲");
                        bean1.setTime(dtstart);
                    }else if("logout".equals(state)){
                        bean1.setState("签出");
                        bean1.setTime(dtstart);
                    }else if("login".equals(state)){
                        bean1.setState("签入");
                        bean1.setTime(dtstart);
                    }else if("rest".equals(state)){
                        bean1.setState("小休");
                        bean1.setTime(dtstart);
                    }else if("after".equals(state)){
                        bean1.setState("话后");
                        bean1.setTime(dtstart);
                    }else if("ringing".equals(state)){
                        bean1.setState("振铃");
                        bean1.setTime(dtstart);
                    }else if("busy".equals(state)){
                        bean1.setState("置忙");
                        bean1.setTime(dtstart);
                    }
                }
                if(i==2){
                    if("idle".equals(state)){
                        bean2.setState("示闲");
                        bean2.setTime(dtstart);
                    }else if("logout".equals(state)){
                        bean2.setState("签出");
                        bean2.setTime(dtstart);
                    }else if("login".equals(state)){
                        bean2.setState("签入");
                        bean2.setTime(dtstart);
                    }else if("rest".equals(state)){
                        bean2.setState("小休");
                        bean2.setTime(dtstart);
                    }else if("after".equals(state)){
                        bean2.setState("话后");
                        bean2.setTime(dtstart);
                    }else if("ringing".equals(state)){
                        bean2.setState("振铃");
                        bean2.setTime(dtstart);
                    }else if("busy".equals(state)){
                        bean2.setState("置忙");
                        bean2.setTime(dtstart);
                    }
                }
                if(i==3){
                    if("idle".equals(state)){
                        bean3.setState("示闲");
                        bean3.setTime(dtstart);
                    }else if("logout".equals(state)){
                        bean3.setState("签出");
                        bean3.setTime(dtstart);
                    }else if("login".equals(state)){
                        bean3.setState("签入");
                        bean3.setTime(dtstart);
                    }else if("rest".equals(state)){
                        bean3.setState("小休");
                        bean3.setTime(dtstart);
                    }else if("after".equals(state)){
                        bean3.setState("话后");
                        bean3.setTime(dtstart);
                    }else if("ringing".equals(state)){
                        bean3.setState("振铃");
                        bean3.setTime(dtstart);
                    }else if("busy".equals(state)){
                        bean3.setState("置忙");
                        bean3.setTime(dtstart);
                    }
                }
                if(i==4){
                    if("idle".equals(state)){
                        bean4.setState("示闲");
                        bean4.setTime(dtstart);
                    }else if("logout".equals(state)){
                        bean4.setState("签出");
                        bean4.setTime(dtstart);
                    }else if("login".equals(state)){
                        bean4.setState("签入");
                        bean4.setTime(dtstart);
                    }else if("rest".equals(state)){
                        bean4.setState("小休");
                        bean4.setTime(dtstart);
                    }else if("after".equals(state)){
                        bean4.setState("话后");
                        bean4.setTime(dtstart);
                    }else if("ringing".equals(state)){
                        bean4.setState("振铃");
                        bean4.setTime(dtstart);
                    }else if("busy".equals(state)){
                        bean4.setState("置忙");
                        bean4.setTime(dtstart);
                    }
                }
                i += 1;
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        return list;
    }
}
