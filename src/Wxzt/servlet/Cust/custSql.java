package Wxzt.servlet.Cust;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Common.table.getCustOrOrderHead;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-6.
 */
public class custSql extends Query{
    /*客户管理页面的查询条件，必填写*/
    public String setCustStrSql(HttpServletRequest request){
        String strSQL = "";
        /*结束码*/
        String CustType = request.getParameter("custType");
        if(CustType!=null&&!CustType.equals("all")){
            strSQL+=" and CustType='"+CustType+"'";
        }
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&&!conditions.equals("")){
            strSQL += "and (CustName = '"+conditions+"' or Mobile = '"+conditions+"' or Telnum = '"+conditions+"')";
        }
        /*权限判定*/
        String IsMgr = request.getParameter("IsMgr");//权限设置:1为个人；2为组别；3为全部
        if(IsMgr.equals("1")){//
            String worknum = request.getParameter("worknum");//坐席工号
            strSQL += " and worknumOwner = '"+worknum+"'";
        }else if(IsMgr.equals("2")){
            String groupid = request.getParameter("groupid");//组别名称ID
            strSQL += " and groupid = '"+groupid+"'";
        }
        return strSQL;
    }
    /*获取客户在左侧的分类，查询条件*/
    public String setStrSqlType(HttpServletRequest request,String StrSqlOther){
        String strSQL = setStrSql(request, StrSqlOther);
        String tableFormType = request.getParameter("tableFormType");
        String worknum = request.getParameter("worknum");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format.format(new Date());
        String Yesterday = format.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        String afterDay = format.format(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        String afterDayofAfter = format.format(new Date(System.currentTimeMillis()+1000*60*60*24*2));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 1);/*本周第一天，以星期日开始*/
        String firstDayofWeek = format.format(c.getTime());
        String firstDayofLastWeek = format.format(new Date(c.getTime().getTime() - 604800000));/*上周日期，604800000 = （1000*60*60*24*7）*/
        c.set(Calendar.DAY_OF_WEEK, 7);
        String endDayofWeek = format.format(new Date(c.getTime().getTime()));
        String endDayofLastWeek = format.format(new Date(c.getTime().getTime()- 604800000));/*上周日期，604800000 = （1000*60*60*24*7）*/
        if(tableFormType==null|| Objects.equals(tableFormType, "")|| Objects.equals(tableFormType, "px_CRM_Cust")){
            return strSQL +" and WorknumOwner is not null ";
        }else if(Objects.equals(tableFormType, "custToMe")){/*我的客户*/
            strSQL += " and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "custPublicPool")){/*公共池*/
            strSQL += " and (WorknumOwner not in (select worknum from dddWork) or  WorknumOwner is null) " +
                    " and (importId not in (select importId from px_CRM_ImportStation where isShow = '0') or importId is null)";
        }else if(Objects.equals(tableFormType, "custGiveToMe")){/*今日新增客户*/
            strSQL += " and worknumOwner = '"+worknum+"' and dtGive>'"+newTime+"'";
        }else if(Objects.equals(tableFormType, "yesterdayGiveToMe")){/*昨日新增*/
            strSQL += " and dtGive>'"+Yesterday+"' and dtGive<'"+newTime+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "weekGiveToMe")){/*本周新增*/
            strSQL += " and dtGive>'"+firstDayofWeek+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "noContact")){/*未联系*/
            strSQL += " and dtContact is null and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "todayContact")){/*今日联系*/
            strSQL += " and dtContact >'"+newTime+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "yesterdayContact")){/*昨日联系*/
            strSQL += " and dtContact <'"+newTime+"' and dtContact>'"+Yesterday+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "weekContact")){/*本周联系*/
            strSQL += " and dtContact>'"+firstDayofWeek+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "lastWeekContact")){/*上周联系*/
            strSQL += " and dtContact>'"+firstDayofLastWeek+"' and dtContact<'"+endDayofLastWeek+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "focus")){/*重点关注*/
            strSQL += " and isFocus=1 and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "todayVisit")){/*今日待回访*/
            strSQL += " and dtVisit>'"+newTime+"' and dtVisit<'"+afterDay+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "afterTodayVisit")){/*明日待回访*/
            strSQL += " and dtVisit>'"+afterDay+"' and dtVisit<'"+afterDayofAfter+"' and worknumOwner = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "weekVisit")){/*本周待回访*/
            strSQL += " and dtVisit>'"+newTime+"' and dtVisit<'"+endDayofWeek+"' and worknumOwner = '"+worknum+"'";
        }
        System.out.println("查询条件："+strSQL);
        return strSQL;
    }
    /*设置客户管理分页查询基础查询语句*/
    public String setCustBasisSql(){
        return " (SELECT m.*,m1.groupid\n" +
                "FROM px_CRM_Cust as m\n" +
                "left join dddWork as m1 on m.worknumOwner=m1.worknum) as cust ";
    }

    /*设置客户管理分页数据表头(除去下拉框)信息*/
    public String[] getCustList(){
        getCustOrOrderHead get = new getCustOrOrderHead();
        String[] otherList = new String[0];
        try {
            otherList = get.getHeadList("CustHead","text");
        } catch (IOException e) {
            otherList = new String[]{"custid","custName","idNum","mobile","telnum","text01","text02","custFrom","custGrade","birthday","text03","text04","text05"};
            e.printStackTrace();
        }
        return otherList;
    }

    /*设置客户管理分页数据下拉框表头信息*/
    public String[] getCustSelectList(){
        getCustOrOrderHead get = new getCustOrOrderHead();
        String[] selectList = new String[0];
        try {
            selectList = get.getHeadList("CustHead","select");
        }catch (IOException e) {
            selectList = new String[]{"select01","sex"};
            e.printStackTrace();
        }
        return selectList;
    }

    /*设置客户管理分页数据坐席表头信息*/
    public String[] getCustWorkList(){
        String[] selectList = {"worknumOwner"};
        return selectList;
    }

    /*设置客户管理分页数据工作组表头信息*/
    public String[] getCustGroupList(){
        String[] selectList = {"groupid"};
        return selectList;
    }

    /*设置客户管理分页数据中下拉框信息的联表查询*/
    public String getCustSelectSql(String sql){
        setSqlSelect_T("a", getCustSelectList(), getCustList(), getCustWorkList(), getCustGroupList(), "px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select concat(a.custDynamicInfoNum,'.['+DATE_FORMAT(a.dtUpdate,'%Y-%m-%d'),']<br>',a.custDynamicInfo) as custDynamicInfo, e.picName,"+sqlHeader+" from ("+sql+") as a "+sqlJoin +" "+
                "left join dddWork as d on a.worknumOwner = d.worknum " +
                "left join dddPic as e on d.picId = e.picId " ;
    }

    /*设置客户管理分页数据中下拉框信息的联表查询*/
    public String getCustSelectSql(String sql,HttpServletRequest request){
        String selectListS = request.getParameter("selectList");
        String textListS = request.getParameter("textList");
        setSqlSelect_T("a",ajaxOrJavaStringList(selectListS, getCustSelectList()),
                ajaxOrJavaStringList(textListS, getCustList()),getCustWorkList(),getCustGroupList(),"px_CRM_Cust");/*设置含有下拉框信息的查询语句*/
        return "select concat(a.custDynamicInfoNum ,'.['+DATE_FORMAT(a.dtUpdate,'%Y-%m-%d'),']<br>',a.custDynamicInfo )as custDynamicInfo, e.picName,"+sqlHeader+" from ("+sql+") as a "+sqlJoin +" "+
                "left join dddWork as d on a.worknumOwner = d.worknum " +
                "left join dddPic as e on d.picId = e.picId " ;
    }

    /*设置客户管理分页数据中下拉框信息的联表查询,无图片信息*/
    public String getCustSelectSqlNoPic(String sql){
        setSqlSelect_T("a", getCustSelectList(), getCustList(), getCustWorkList(), getCustGroupList(), "px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select concat(a.custDynamicInfoNum,'.['+DATE_FORMAT(a.dtUpdate,'%Y-%m-%d'),']<br>',a.custDynamicInfo ) as custDynamicInfo, "+sqlHeader+" from ("+sql+") as a "+sqlJoin +" "+
                "left join dddWork as d on a.worknumOwner = d.worknum ";
    }

    /*设置客户管理分页数据中下拉框信息的联表查询,无图片信息*/
    public String getCustSelectSqlNoPic(String sql,HttpServletRequest request){
        String selectListS = request.getParameter("selectList");
        String textListS = request.getParameter("textList");
        setSqlSelect_T("a",ajaxOrJavaStringList(selectListS, getCustSelectList()),
                ajaxOrJavaStringList(textListS, getCustList()),getCustWorkList(),getCustGroupList(),"px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select concat(a.custDynamicInfoNum,'.['+DATE_FORMAT(a.dtUpdate,'%Y-%m-%d'),']<br>'" +
                ",a.custDynamicInfo ) as custDynamicInfo, "+sqlHeader+" from ("+sql+") as a "+sqlJoin +" "+
                "left join dddWork as d on a.worknumOwner = d.worknum ";
    }
    /*客户管理分页查询*/
    public Object custQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setCustStrSql(request));
        strSql = setStrSqlType(request,strSql);
        /*基础信息查询表*/
        String dataTable = setCustBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "custid", "dtUpdate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getCustSelectSqlNoPic(sql,request);
        System.out.println("客户查询语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        /*添加动态*/
        String selectListS = request.getParameter("selectList");
        String textListS = request.getParameter("textList");
        String[] list1;
        list1 = addList(ajaxOrJavaStringList(selectListS, getCustSelectList()),ajaxOrJavaStringList(textListS, getCustList()));
        ArrayList<String> list = stringlistToArray(list1);
        list.add("custDynamicInfo");
        /*添加工作组以及归属坐席*/
        list.add("worknumOwner");
        list.add("groupid");
//        list.add("picName");
        json.setDataList(sqlListData(sql, arrayToStringlist(list), "cust"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("cust"+"ToExcel",custExcel(strSql));
        json.setSqlTable("cust");
        return json;
    }

    /*客户联系人分页查询*/
    public Object custContactQuery(HttpServletRequest request){
        /*获取查询条件*/
        String custnum = request.getParameter("custnum");
        String strSql = " and custNum = '"+custnum+"'";
        /*基础信息查询表*/
        String dataTable = " px_CRM_CustContact ";
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "custid", "dtUpdate");
        /*分页数据中下拉框信息的联表查询*/
//        sql = getCustSelectSqlNoPic(sql,request);
        System.out.println("联系人查询语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        /*添加动态*/
        String selectListS = request.getParameter("selectList");
        String textListS = request.getParameter("textList");
        String[] list1;
        list1 = addList(ajaxOrJavaStringList(selectListS, getCustSelectList()),ajaxOrJavaStringList(textListS, getCustList()));
        ArrayList<String> list = stringlistToArray(list1);
//        list.add("custDynamicInfo");
        /*添加工作组以及归属坐席*/
//        list.add("worknumOwner");
//        list.add("groupid");
        json.setDataList(sqlListData(sql, arrayToStringlist(list), "cust"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("custContact"+"ToExcel",custExcel(strSql));
        json.setSqlTable("custContact");
        return json;
    }

    /*打开客户页面时获取客户数据*/
    public Object custOneQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = "";
        /*客户管理页面双击打开客户资料页面*/
        String custid = request.getParameter("id");
        if(custid!=null&&!custid.equals("")){
            strSql = " and custid = '"+custid+"'";
        }
        System.out.println("-----------------custid---------------"+custid);
        /*来电弹屏*/
        String mobile = request.getParameter("mobile");
        if(mobile!=null&&!mobile.equals("")){
            String mobile_1 = "0"+mobile;//外地加0的号码
            ArrayList<String> list = new ArrayList<>();
            list.add("'"+mobile+"'");
            list.add("'"+mobile_1+"'");
            strSql = " and custnum in (select custnum from v_custContact where (Mobile='"+mobile+"' or Telnum ='"+mobile+"' or Mobile='"+mobile_1+"' or Telnum ='"+mobile_1+"')) ";
        }
        /*基础信息查询表*/
        String dataTable = setCustBasisSql();
        /*查询单个数据语句*/
        String sql = setOneSql(dataTable, strSql);
        sql = "select a.*,c.picName from ("+sql+") as a LEFT JOIN dddWork AS b ON a.worknumowner = b.worknum left join dddPic as c on b.picId = c.picId";
        System.out.println("客户管理获取数据语句：" + sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getCustSelectList(),getCustList());
        String[] list2 = addList(getCustWorkList(),getCustGroupList());
        String[] list3 = {"picName"};
        list2 = addList(list2,list3);
        json.setDataList(sqlListData(sql, addList(list2, list1), "cust"));
        return json;
    }

    /*编辑联系人数据*/
    public Object custConOneQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = "";
        /*客户管理页面双击打开客户资料页面*/
        String custid = request.getParameter("id");
        if(custid!=null&&!custid.equals("")){
            strSql = " and custid = '"+custid+"'";
        }
        /*基础信息查询表*/
        String dataTable = " px_CRM_CustContact ";
        /*查询单个数据语句*/
        String sql = setOneSql(dataTable, strSql);
        System.out.println("单个联系人获取数据语句：" + sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getCustSelectList(), getCustList());
        json.setDataList(sqlListData(sql, list1, "cust"));
        return json;
    }

    /*获取客户管理分页查询的导出Excel语句*/
    public String custExcel(String str) {
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setCustBasisSql(), str, "dtUpdate");
        /*分页数据中下拉框信息的联表查询*/
        setSqlSelect_T("a", getCustSelectList(), getCustList(), getCustWorkList(), getCustGroupList(), "px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select concat(a.custDynamicInfoNum,'.['+DATE_FORMAT(a.dtUpdate,'%Y-%m-%d'),']<br>',a.custDynamicInfo) as custDynamicInfo,"+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*客户回收语句*/
    public boolean custRecycl(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        String id = request.getParameter("id");
        String sql = "update px_CRM_Cust set dtGive='"+newTime+"',dtUpdate='"+newTime+"',WorknumOwner='0' where Custid="+id;
        return updataSql(sql);
    }

    /*客户删除语句*/
    public boolean custDel(HttpServletRequest request){
        String id = request.getParameter("id");
        /*删除动态数据*/
        String sql = "delete from px_CRM_CustDynamic where custnum in (select custnum from px_CRM_Cust where Custid="+id+")";
        System.out.println("删除动态数据："+sql);
        boolean b = updataSql(sql);
        String sql1 = "delete from px_CRM_Cust where Custid="+id;
        System.out.println("删除客户数据："+sql1);
        boolean b1 = updataSql(sql1);
        return b&&b1;
    }

    /*客户管理更新语句*/
    public boolean updateCust(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        request.setAttribute("dtUpdate", newTime);
//        String worknumOwner = request.getParameter("worknumOwner");
        ArrayList<String> setList = addListArray(getCustSelectList(), getCustList());
        /*不更新id和custNum*/
        //setList.remove("custid");
        //setList.remove("custNum");
        /*新增归属坐席*/
        setList.add("worknumOwner");
        setList.add("custDynamicInfo");
        setList.add("custDynamicInfoNum");
        String str = "";
        String id = request.getParameter("custid"),num = request.getParameter("custNum");
        if(id!=null&& !Objects.equals(id, "")){
            str = " custid='"+id+"'";
        }else {
            str = " custnum='"+num+"'";
        }
        String sql = "update px_CRM_Cust "+setOneUpdate(request,setList)+",dtUpdate='"+newTime+"' where "+str;
        String action = request.getParameter("action");
        if(Objects.equals(action, "3")){/*添加动态时，更新联系时间*/
            sql = "update px_CRM_Cust "+setOneUpdate(request,setList)+",dtUpdate='"+newTime+"',dtContact='"+newTime+"' where "+str;
        }
        System.out.println("更新语句："+sql);
        return updataSql(sql);
    }

    /*客户管理添加客户*/
    public boolean insertCust(HttpServletRequest request){
        boolean insert = false;
        /*判断待添加电话是否已存在*/
        String mobile = request.getParameter("mobile");
        String telnum = request.getParameter("telnum");
        ArrayList<String> custList = new ArrayList<>();
        if(mobile!=null&& !Objects.equals(mobile, "")){
            custList.add("'"+mobile+"'");
        }
        if(telnum!=null&& !Objects.equals(telnum, "")){
            custList.add("'"+telnum+"'");
        }
        try {
            ArrayList<Object> custObject = queryRepeatCust(custList);
            if(custObject.size()==0){//不存在新客户
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String newTime = format.format(new Date());
                String[] setList = addList(getCustSelectList(), getCustList());
                /*删除custid*/
                ArrayList<String> list = stringlistToArray(setList);
                list.remove("custid");
                setList = arrayToStringlist(list);
                /*添加需要后台生成的字段*/
                String[] addName = {"dtUpdate","dtGive","dtCreate","IsCall","worknumOwner"};
                String[] addValue = {newTime,newTime,newTime,"1",request.getParameter("worknumOwner")};
                String insertList = setOneInsert(request, setList, addName, addValue);
                String sql = "INSERT  into px_CRM_Cust "+insertList;
                System.out.println("添加语句："+sql);
                insert = updataSql(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return insert;
        }
    }

    /*客户页面提取*/
    public boolean extractCust(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        String workNum = request.getParameter("workNum");
        /*查询可提取额度*/
        String countSql = " select value from dddConfig where config = 'extract'";
        String count = sqlOneData(countSql);
        if(count==null|| Objects.equals(count, "null")){
            count = "0";
        }
        String sql = "update px_CRM_Cust set worknumOwner='"+workNum+"' ,dtGive='"+newTime+"',dtUpdate='"+newTime+"' " +
                "where custid in (select ab.custid from (select custid from px_CRM_Cust " +
                "where WorknumOwner not in (select worknum from dddWork) or  WorknumOwner is null  " +
                "and importId in (select importId from px_CRM_ImportStation where isShow = '1') limit "+count +
                ") as ab )";
        System.out.println("客户页面提取："+sql);
        return updataSql(sql);
    }

    /*弹屏页面提取*/
    public boolean extractCustOfOne(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        String workNum = request.getParameter("workNum");
        String custid = request.getParameter("custid");
        String sql = "update px_CRM_Cust set worknumOwner='"+workNum+"' ,dtGive='"+newTime+"',dtUpdate='"+newTime+"' " +
                "where custid="+custid;
        return updataSql(sql);
    }

    /*关注*/
    public boolean focusCust(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        String isFocus = request.getParameter("isFocus");
        String custid = request.getParameter("custid");
        String sql = "update px_CRM_Cust set isFocus="+isFocus+" ,dtGive='"+newTime+"',dtUpdate='"+newTime+"' " +
                "where custid="+custid;
        return updataSql(sql);
    }

    /*获取来电弹屏头部信息相关数据*/
    public Object queryScreenHead(HttpServletRequest request){
        String hotLine = request.getParameter("hotLine");
        String dest = request.getParameter("dest");
        if(dest==null|| Objects.equals(dest, "")){
            dest = "1";
        }else {
            dest = dest.substring(0);
        }
        String sql = "select * from px_CRM_CustHead where hotLine='"+hotLine+"' and dest ='"+dest+"'";
        System.out.println("来电弹屏头部信息查询："+sql);
        queryJson json = new queryJson();
        String[] list1 = {"hotLine","callInLine","dest","greetings","note"};
        json.setDataList(sqlListData(sql, list1, "custHead"));
        return json;
    }

    /*添加联系人*/
    public boolean insertCustCon(HttpServletRequest request){
        boolean insert = false;
        /*判断待添加电话是否已存在*/
        String mobile = request.getParameter("mobile");
        String telnum = request.getParameter("telnum");
        ArrayList<String> custList = new ArrayList<>();
        if(mobile!=null&& !Objects.equals(mobile, "")){
            custList.add("'"+mobile+"'");
        }
        if(telnum!=null&& !Objects.equals(telnum, "")){
            custList.add("'"+telnum+"'");
        }
        try {
            ArrayList<Object> custObject = queryRepeatCust(custList);
            if(custObject.size()==0){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String newTime = format.format(new Date());
                String[] setList = addList(getCustSelectList(), getCustList());
                /*删除custid*/
                ArrayList<String> list = stringlistToArray(setList);
                list.remove("custid");
                setList = arrayToStringlist(list);
                /*添加需要后台生成的字段*/
                String[] addName = {"dtUpdate","dtGive","dtCreate"};
                String[] addValue = {newTime,newTime,newTime};
                String insertList = setOneInsert(request, setList, addName, addValue);
                String sql = "INSERT  into px_CRM_CustContact "+insertList;
                System.out.println("添加联系人语句：" + sql);
                insert = updataSql(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return insert;
        }

    }

    /*更新联系人*/
    public boolean updateCustCon(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        request.setAttribute("dtUpdate", newTime);
        ArrayList<String> setList = addListArray(getCustSelectList(), getCustList());
        /*不更新id和custNum*/
        setList.remove("custid");
        setList.remove("custNum");
        String str = "";
        String id = request.getParameter("id");
        str = " custid='"+id+"'";
        String sql = "update px_CRM_CustContact "+setOneUpdate(request,setList)+",dtUpdate='"+newTime+"' where "+str;
        System.out.println("更新联系人语句："+sql);
        return updataSql(sql);
    }

    /*删除联系人*/
    public boolean delCustCon(HttpServletRequest request){
        String id = request.getParameter("id");
        String sql1 = "delete from px_CRM_CustContact where Custid="+id;
        System.out.println("删除客户数据："+sql1);
        return updataSql(sql1);
    }

    /*查询排重数据*/
    public ArrayList<Object> queryRepeatCust(ArrayList<String> telList) throws SQLException {
        String telListString = join(telList,",");
        String sql = "select mobile,telnum,custnum from v_custContact where (mobile in ("+telListString+") or telnum in ("+telListString+"))";
        String[] list = {"mobile","telnum","custnum"};
        return sqlListDataError(sql, list, "custRepeat");
    }
    /*查询排重数据*/
    public ArrayList<Object> queryRepeatCustForDB(String sqlStr) throws SQLException {
        String sql = "select mobile,telnum,custnum from v_custContact where (mobile in ("+sqlStr+") or telnum in ("+sqlStr+"))";
        String[] list = {"mobile","telnum","custnum"};
        return sqlListDataError(sql, list, "custRepeat");
    }
}
