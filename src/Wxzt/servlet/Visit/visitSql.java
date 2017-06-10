package Wxzt.servlet.Visit;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-11.
 */
public class visitSql extends Query{
    /*客户回访页面的查询条件，必填写*/
    public String getVisitStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&&!conditions.equals("")){
            strSQL += "and custName like '%"+conditions+"%'";
        }
        /*权限判定,默认查看本人的回访时间*/
        String worknum = request.getParameter("worknum");//坐席工号
        strSQL += " and Worknum = '"+worknum+"'";
//        String IsMgr = request.getParameter("IsMgr");//权限设置:1为个人；2为组别；3为全部
//        if(IsMgr.equals("1")){//
//            String worknum = request.getParameter("worknum");//坐席工号
//            strSQL += " and Worknum = '"+worknum+"'";
//        }else if(IsMgr.equals("2")){
//            String groupid = request.getParameter("groupid");//组别名称ID
//            strSQL += " and groupid = '"+groupid+"'";
//        }
        /*时间判断*/
        String timeSQL = "";
        String firstDt = request.getParameter("firstDt");//开始时间
        if(firstDt!=null&& !Objects.equals(firstDt, "")){
            timeSQL += " and dtVisit>'"+firstDt+"'";
        }
        String endDt = request.getParameter("endDt");//结束时间
        if(endDt!=null&& !Objects.equals(endDt, "")){
            timeSQL += " and dtVisit<'"+endDt+"'";
        }
        /*打开时默认显示今天时间*/
        SimpleDateFormat format_2 = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format_2.format(new Date());//今天
        String endTime = format_2.format(new Date(System.currentTimeMillis()+1000*60*60*24));//明天
        String QueryName = request.getParameter("buttonType");//开始时间
        if(Objects.equals(QueryName, "today")){
            timeSQL = " and dtVisit>='"+newTime+"' and dtVisit<'"+endTime+"'";
        }
        return strSQL+timeSQL+" and dtvisit !='' and dtvisit is not null";
    }

    /*设置客户回访分页查询基础查询语句*/
    public String getVisitBasisSql(){
        return " (select m.custName,m.dtVisit,m.mobile,m.custid,m.dtUpdate,m1.groupid,m1.worknum from px_CRM_Cust as m " +
                "left join dddWork as m1 on m.WorknumOwner = m1.worknum) as visit ";
    }

    /*设置客户回访表头(除去下拉框)信息*/
    public String[] getVisitList(){
        String[] otherList = {"custName","dtVisit","mobile"};
        return otherList;
    }

    /*设置客户回访归属人表头信息*/
    public String[] getVisitWorkList(){
        String[] selectList = {"worknum"};
        return selectList;
    }

    /*设置客户回访归属工作组表头信息*/
    public String[] getVisitGroupList(){
        String[] selectList = {"groupid"};
        return selectList;
    }

    /*设置客户回访分页数据中下拉框信息的联表查询*/
    public String getVisitSelectSql(String sql){
        String[] selectList = {};
        String[] otherList = getVisitList();
        setSqlSelect_T("a", selectList, otherList, getVisitWorkList(),getVisitGroupList(), "px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*客户管理分页查询*/
    public Object visitQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, getVisitStrSql(request));
        /*基础信息查询表*/
        String dataTable = getVisitBasisSql();
        /*初始化页面*/
        init(request, dataTable, strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "custid", "dtUpdate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getVisitSelectSql(sql);
        System.out.println("回访客户查询："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getVisitList(), getVisitWorkList()), "visit"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        return json;
    }
    public String visitOneQuery(HttpServletRequest request){
        String worknum = request.getParameter("worknum");
        SimpleDateFormat format_2 = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format_2.format(new Date());//今天
        String endTime = format_2.format(new Date(System.currentTimeMillis()+1000*60*60*24));//明天
        String sqlCount = "select count(*) from px_CRM_Cust where dtvisit > '"+newTime+"' and dtVisit<'"+endTime+"' and worknumowner = '"+worknum+"' and dtvisit !='' and dtvisit is not null";
        System.out.println("查询当日客户待回访量语句："+sqlCount);
        return sqlOneData(sqlCount);
    }
}
