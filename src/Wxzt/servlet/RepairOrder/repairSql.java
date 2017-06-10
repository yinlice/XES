package Wxzt.servlet.RepairOrder;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Common.table.getCustOrOrderHead;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-12.
 */
public class repairSql extends Query {
    /*工单查询的查询条件，必填写*/
    public String setRepairStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&& !Objects.equals(conditions, "")){
            strSQL+=" and (custname = '"+conditions+"' or custTel = '"+conditions+"') ";
        }
        /*工单进度*/
        String state = request.getParameter("state");
        if(state!=null&&!state.equals("all")){
            strSQL += " and state = '"+state+"'";
        }
        /*时间判断*/
        String timeSQL = "";
        String firstDt = request.getParameter("firstDt");//开始时间
        if(firstDt!=null&& !Objects.equals(firstDt, "")){
            timeSQL += " and creatTime>'"+firstDt+"'";
        }
        String endDt = request.getParameter("endDt");//结束时间
        if(endDt!=null&& !Objects.equals(endDt, "")){
            timeSQL += " and creatTime<'"+endDt+"'";
        }
        /*打开时默认显示今天时间*/
        SimpleDateFormat format_2 = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format_2.format(new Date());//今天
        String endTime = format_2.format(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));//明天
        String QueryName = request.getParameter("buttonType");
        if(Objects.equals(QueryName, "today")){
            timeSQL = " and creatTime>='"+newTime+"' and creatTime<'"+endTime+"'";
        }
        /*工单对象编号*/
        String custnum = request.getParameter("custnum");
        if(custnum!=null&& !Objects.equals(custnum, "")){
            strSQL += " and custnum = '"+custnum+"'";
        }
        return strSQL+timeSQL+" and creatTime !='' and creatTime is not null";
    }

    /*获取查询条件*/
    public String setStrSql(HttpServletRequest request,String StrSqlOther){
        String AdvancedQuery = request.getParameter("otherQueryCondition");//高级查询
        if(AdvancedQuery!=null&& !Objects.equals(AdvancedQuery, "")){
            /*替换custname*/
            String pattern = "groupidReceive (=)'(.*?)'";
            String str = AdvancedQuery.replaceAll(pattern, "worknumReceive in (select worknum from dddWork where groupid $1 '$2')");
            return  " and "+str;
        }else {
            return StrSqlOther;//显示在不同页面上的查询条件
        }
    }

    /*获取页面分类查询条件*/
    public String setStrSqlType(HttpServletRequest request,String StrSqlOther){
        String strSQL = setStrSql(request, StrSqlOther);
        /*权限判定*/
        String IsMgr = request.getParameter("IsMgr");//权限设置:1为个人；2为组别；3为全部
        String groupid = request.getParameter("groupid");//组别名称ID
        String worknum = request.getParameter("worknum");
        String tableFormType = request.getParameter("tableFormType");
        if(tableFormType==null|| Objects.equals(tableFormType, "")|| Objects.equals(tableFormType, "inbox-my")){//我的收件工单
            strSQL += " and worknumReceive = '"+worknum+"' ";
        }else if(Objects.equals(tableFormType, "inbox")){//收件工单
            if(IsMgr.equals("1")){
                strSQL += " and worknumReceive = '"+worknum+"' ";
            }else if(IsMgr.equals("2")){
                strSQL += " and worknumReceive in (select worknum from dddWork where groupid = '"+groupid+"' )" ;
            }
        }else if(Objects.equals(tableFormType, "outbox")){//发件工单
            if(IsMgr.equals("1")){
                strSQL += " and worknumCreat = '"+worknum+"'";
            }else if(IsMgr.equals("2")){
                strSQL += " and worknumCreat in (select worknum from dddWork where groupid = '"+groupid+"')";
            }
        }else if(Objects.equals(tableFormType, "inbox-dealWith")){//收件-处理中
            strSQL += " and worknumReceive = '"+worknum+"' and state='1'";
        }else if(Objects.equals(tableFormType, "inbox-complete")){//收件-已完成
            strSQL += " and worknumReceive = '"+worknum+"' and state='97'";
        }else if(Objects.equals(tableFormType, "inbox-focus")){//收件-重点
            strSQL += " and worknumReceive = '"+worknum+"' and isFocus='1'";
        }else if(Objects.equals(tableFormType, "outbox-my")){//发送-我的工单
            strSQL += " and worknumCreat = '"+worknum+"'";
        }else if(Objects.equals(tableFormType, "outbox-undistributed")){//发送-未分配
            strSQL += " and worknumCreat = '"+worknum+"' and state='0'";
        }else if(Objects.equals(tableFormType, "outbox-dealWith")){//发送-处理中
            strSQL += " and worknumCreat = '"+worknum+"' and state='1'";
        }else if(Objects.equals(tableFormType, "outbox-complete")){//发送-已完成
            strSQL += " and worknumCreat = '"+worknum+"' and state='97'";
        }else if(Objects.equals(tableFormType, "outbox-focus")){//发送-关注
            strSQL += " and worknumCreat = '"+worknum+"' and isFocus='1'";
        }
        return strSQL;
    }

    /*设置工单查询数据表头(除去下拉框)信息*/
    public String[] getRepairList(){
        getCustOrOrderHead get = new getCustOrOrderHead();
        String[] otherList = new String[0];
        try {
            otherList = get.getHeadList("OrderHead","text");
        } catch (IOException e) {
            otherList = new String[]{"id","gdnum","gdtheme","creatTime","custTel","endTime","questionsBriefly","custname","note","isFocus"};
            e.printStackTrace();
        }
        return otherList;
    }

    /*设置工单查询数据下拉框表头信息*/
    public String[] getRepairSelectList(){
        getCustOrOrderHead get = new getCustOrOrderHead();
        String[] selectList = new String[0];
        try {
            selectList = get.getHeadList("OrderHead","select");
        } catch (IOException e) {
            selectList = new String[]{"businessType","problemType","state","emergency"};
            e.printStackTrace();
        }
        return selectList;
    }

    /*设置工单查询数据坐席表头信息*/
    public String[] getRepairWorkList(){
        String[] selectList = {"worknumCreat","worknumReceive","worknumRecipient"};
        return selectList;
    }

    /*设置工单查询数据工作组表头信息*/
    public String[] getRepairGroupList(){
//        String[] selectList = {"groupidReceive"};
        String[] selectList = {};
        return selectList;
    }

    /*设置工单查询基础查询语句*/
    public String getRepairBasisSql(){
//        return " (select m.*,m1.groupid as groupidReceive from px_crm_gd as m " +
//                "left join dddWork as m1 on m.worknumReceive=m1.worknum) as repair ";
        return " px_crm_gd ";
    }

    /*设置工单查询数据中下拉框信息的联表查询*/
    public String getRepairSelectSql(String sql){
        setSqlSelect_T("a",getRepairSelectList(),getRepairList(),getRepairWorkList(),getRepairGroupList(),"px_CRM_GD");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select m1.groupid as groupidReceive,"+sqlHeader+" from ("+sql+") as a "+sqlJoin+
                " left join dddWork as m1 on a.worknumReceive=m1.worknum";
    }

    /*根据参数设置联表查询*/
    public String getRepairSelectSql(String sql,HttpServletRequest request){
        String selectListS = request.getParameter("selectList");
        String textListS = request.getParameter("textList");
        setSqlSelect_T("a",ajaxOrJavaStringList(selectListS, getRepairSelectList()),
                ajaxOrJavaStringList(textListS, getRepairList()),getRepairWorkList(),getRepairGroupList(),"px_CRM_GD");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select m1.groupid as groupidReceive,"+sqlHeader+" from ("+sql+") as a "+sqlJoin+
                " left join dddWork as m1 on a.worknumReceive=m1.worknum";
    }

    /*工单查询*/
    public Object repairQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSqlType(request, setRepairStrSql(request));
        /*基础信息查询表*/
        String dataTable = getRepairBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "creatTime");
        System.out.println("查询工单分页语句："+sql);
        /*分页数据中下拉框信息的联表查询*/
        sql = getRepairSelectSql(sql,request);
        System.out.println("查询工单语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        /*设置传递的表头参数*/
        String selectListS = request.getParameter("selectList");
        String textListS = request.getParameter("textList");
        String[] list1 ,list2;
        list1 = addList(ajaxOrJavaStringList(selectListS,getRepairSelectList()),ajaxOrJavaStringList(textListS,getRepairList()));
        list2 = addList(getRepairWorkList(), getRepairGroupList());
        /*添加工作组*/
        String[] list3 = {"groupidReceive"};
        list2 = addList(list2,list3);
        json.setDataList(sqlListData(sql, addList(list1, list2), "repair"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("repair" + "ToExcel", repairExcel(strSql));
        json.setSqlTable("repair");
        return json;
    }

    /*打开客户页面时获取客户数据*/
    public Object repairOneQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = "";
        /*客户管理页面双击打开客户资料页面*/
        String id = request.getParameter("id");
        String num = request.getParameter("num");
        if(id!=null&&!id.equals("")&&num!=null&&!num.equals("")){
            strSql = " and (id = '"+id+"' or gdnum ='"+num+"')";
        }else if(id!=null&&!id.equals("")){
            strSql = " and id = '"+id+"'";
        }else if(num!=null&&!num.equals("")){
            strSql += " and gdnum = '"+num+"'";
        }
        /*基础信息查询表*/
        String dataTable = getRepairBasisSql();
        /*查询单个数据语句*/
        String sql = setOneSql(dataTable, strSql);
        /*联表查询工作组id*/
        sql = "select m1.groupid as groupidReceive,a.* from ("+sql+") as a "+
                " left join dddWork as m1 on a.worknumReceive=m1.worknum";
        System.out.println("编辑工单语句：" + sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getRepairList(),getRepairSelectList());
        String[] list2 = addList(getRepairWorkList(), getRepairGroupList());
        /*添加工作组*/
        String[] list3 = {"groupidReceive"};
        list2 = addList(list2,list3);
        json.setDataList(sqlListData(sql, addList(list1, list2), "repair"));
        return json;
    }

    /*获取工单分页查询的导出Excel语句*/
    public String repairExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(getRepairBasisSql(), str, "creatTime");
        /*分页数据中下拉框信息的联表查询*/
        sql = getRepairSelectSql(sql);
        return sql;
    }

    /*工单删除语句*/
    public boolean repairDel(HttpServletRequest request){
        String id = request.getParameter("id");
        /*删除工单处理数据*/
        String sql = "delete from px_CRM_GDStation where gdnum in (select gdnum from px_CRM_GD where id="+id+")";
        System.out.println("删除工单处理数据："+sql);
        boolean b = updataSql(sql);
        String sql1 = "delete from px_CRM_GD where ID="+id;
        System.out.println("删除工单数据："+sql1);
        boolean b1 = updataSql(sql1);
        return b&&b1;
    }

    /*工单更新语句*/
    public boolean repairUpdate(HttpServletRequest request){
        /*不更新id*/
        ArrayList<String> repairList = stringlistToArray(getRepairList());
        repairList.remove("id");
        String[] list1 = addList(getRepairWorkList(), getRepairSelectList());
        String sql = "update px_CRM_GD "+setOneUpdate(request,addListArray(list1, arrayToStringlist(repairList)))+" where ID='"+request.getParameter("id")+"'";
        System.out.println("工单更新语句：" + sql);
        return updataSql(sql);
    }

    /*工单添加*/
    public boolean repairInsert(HttpServletRequest request){
        /*不添加id*/
        ArrayList<String> repairList = stringlistToArray(getRepairList());
        repairList.remove("id");
        String[] list1 = addList(getRepairWorkList(), getRepairSelectList());
        /*添加需要后台生成的字段*/
        String[] addList = {"custnum"};
        String[] valueList = {request.getParameter("custnum")};
        String insertList = setOneInsert(request, addList(list1, arrayToStringlist(repairList)),addList,valueList);
        String sql = "INSERT  into px_CRM_GD "+insertList;
        System.out.println("工单添加语句："+sql);
        return updataSql(sql);
    }

    /*--------------------------------------工单处理过程-----------------------------------------------*/
    /*工单处理记录添加*/
    public boolean repairStationInsert(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        String[] list = {"gdnum","state","gdInfor","custTel","emergency","problemType","updataWorknum","worknumReceive","worknumRecipient"};
        String[] addList = {"dtUpdate"};
        String[] valueList = {newTime};
        String insertList = setOneInsert(request, list, addList, valueList);
        String sql = "INSERT  into px_CRM_GdStation "+insertList;
        System.out.println("工单处理过程添加语句："+sql);
        return updataSql(sql);
    }

    /*工单处理过程查询*/
    public Object repairStationQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = " and gdnum='"+request.getParameter("gdnum")+"'";
        /*基础信息查询表*/
        String dataTable = " px_CRM_GDStation ";
        /*查询分页数据语句*/
        String sql = setOneSql(dataTable, strSql);
        /*分页数据中下拉框信息的联表查询*/
        String[] list = {"gdnum","gdInfor","dtUpdate"};
        String[] selectList = {"state"};
        String[] workList = {"updataWorknum","worknumReceive","worknumRecipient"};
        String[] groupList = {};
        setSqlSelect_T("a", selectList, list, workList, groupList, "px_crm_gd");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        sql = "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin+" order by dtUpdate desc";
        System.out.println("查询工单处理过程语句：" + sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(list, selectList);
        json.setDataList(sqlListData(sql, addList(list1, workList), "repairSta"));
        return json;
    }
}
