package Wxzt.servlet.dynamic;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2015/12/6.
 */
public class dynamicSql extends Query {
    /*客户动态的查询条件，必填写*/
    public String setDynStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&&!conditions.equals("")){
            strSQL += "and (custid in (select custnum from px_crm_cust where custname like '%"+conditions+"%') or custDynamicInfo like '%"+conditions+"%')";
        }
        /*客户编号*/
        String custnum = request.getParameter("custnum");
        if(custnum!=null&& !Objects.equals(custnum, "")){
            strSQL+=" and custnum='"+custnum+"'";
        }
        /*设置高级查询中的客户名称*/
        String AdvancedQuery = request.getParameter("otherQueryCondition");//高级查询
        if(AdvancedQuery!=null&& !Objects.equals(AdvancedQuery, "")){
            strSQL+=" and custnum in (select custnum from px_crm_cust where custname like '%"+AdvancedQuery+"%')";
        }
        return strSQL;
    }

    /*获取查询条件*/
    public String setStrSql(HttpServletRequest request,String StrSqlOther){
        String AdvancedQuery = request.getParameter("otherQueryCondition");//高级查询
        if(AdvancedQuery!=null&& !Objects.equals(AdvancedQuery, "")){
            /*替换custname*/
            String pattern = "custname (like|=|!=)'(.*?)'";
            String str = AdvancedQuery.replaceAll(pattern, "custnum in (select custnum from px_crm_cust where custname $1 '$2')");
            return  " and "+str;
        }else {
            return StrSqlOther;//显示在不同页面上的查询条件
        }
    }

    /*设置客户动态基础查询语句*/
    public String setDynBasisSql(){
        //a.dtCreate
        return " select a.id,a.WorknumCreate,a.CustDynamicInfo,DATE_FORMAT(a.dtCreate,'%Y-%m-%d %H:%m:%s') as dtCreate," +
                "(b.workname+'('+b.worknum+')') as workname,c.picName " +
                "from px_CRM_CustDynamic as a " +
                "left join dddwork as b on a.WorknumCreate=b.worknum left join dddPic as c on b.picId = c.picId ";
    }

    /*设置客户动态数据表头*/
    public String[] getDynList(){
        String[] otherList = {"custDynamicInfo","dtCreate","workname","worknumCreate","id","picName"};
        return otherList;
    }

    /*客户动态查询*/
    public Object dynQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setDynStrSql(request);
        /*基础信息查询表*/
        String dataTable = setDynBasisSql();
        /*查询分页数据语句*/
        String sql = dataTable + " where 1=1 "+strSql +" order by dtCreate desc";
        System.out.println("获取动态语句:"+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, getDynList(), "dyn"));
        return json;
    }

    /*设置客户分页基础查询语句*/
    public String setDynTableBasisSql(){
        return " px_CRM_CustDynamic ";
    }

    /*设置动态查询数据表头(除去下拉框)信息*/
    public String[] getDynTableList(){
        String[] otherList = {"custDynamicInfo","dtCreate","custnum","id"};
        return otherList;
    }

    /*设置工单查询数据下拉框表头信息*/
    public String[] getDynTableSelectList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置工单查询数据坐席表头信息*/
    public String[] getDynTableWorkList(){
        String[] selectList = {"worknumCreate"};
        return selectList;
    }

    /*设置工单查询数据工作组表头信息*/
    public String[] getDynTableGroupList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置工单查询数据中下拉框信息的联表查询*/
    public String getRosSelectSql(String sql){
        setSqlSelect_T("a",getDynTableSelectList(),getDynTableList(),getDynTableWorkList(),getDynTableGroupList(),"px_CRM_GD");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select m.custname,m.mobile,"+sqlHeader+" from ("+sql+") as a "+sqlJoin+
                " left join px_crm_cust as m on a.custnum = m.custnum ";
    }

    /*客户动态分页查询*/
    public Object dynTableQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setDynStrSql(request));
        /*基础信息查询表*/
        String dataTable = setDynTableBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "dtCreate");
        System.out.println("查询工单处理过程分页语句："+sql);
        /*分页数据中下拉框信息的联表查询*/
        sql = getRosSelectSql(sql);
        System.out.println("查询工单处理过程语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        ArrayList<String> list1 = stringlistToArray(getDynTableList());
        /*添加客户名称custname*/
        list1.add("custname");
        list1.add("mobile");
        json.setDataList(sqlListData(sql, addList(arrayToStringlist(list1),getDynTableWorkList()), "dyn"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("dynamic" + "ToExcel", dynamicExcel(strSql));
        json.setSqlTable("dynamic");
        return json;
    }

    /*获取工单分页查询的导出Excel语句*/
    public String dynamicExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setDynTableBasisSql(), str, "dtCreate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getRosSelectSql(sql);
        return sql;
    }

    /*客户动态添加*/
    public boolean dynUpdata(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        /*添加需要后台生成的字段*/
        String[] addName = {"dtCreate","custnum","worknumCreate"};
        String[] addValue = {newTime,request.getParameter("custNum"),request.getParameter("worknum")};
        String insertList = setOneInsert(request, getDynList(), addName, addValue);
        String sql = "INSERT  into px_CRM_CustDynamic "+insertList;
        System.out.println("添加语句============："+sql);
        return updataSql(sql);
    }

    /*客户动态更新*/
    public boolean dynUpdata_2(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        String sql = "update px_CRM_CustDynamic set CustDynamicInfo='"+request.getParameter("info")+"',dtCreate='"+newTime+"' where id='"+request.getParameter("id")+"'";
        System.out.println("添加语句："+sql);
        return updataSql(sql);
    }
}
