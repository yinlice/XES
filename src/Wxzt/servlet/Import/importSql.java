package Wxzt.servlet.Import;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Cust.custSql;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-11.
 */
public class importSql extends custSql {
    /*公共池页面的查询条件，必填写*/
    public String setImportStrSql(HttpServletRequest request){
        String strSQL = "";
        /*导入ID*/
        String ImportID = request.getParameter("ImportID");
        if(!ImportID.equals("all")){
            strSQL+="and ImportID='"+ImportID+"'";
        }
        /*文本输入框*/
        String Conditions = request.getParameter("Conditions");
        if(!Conditions.equals("")){
            /*输入条件种类:1为姓名,2为电话*/
            String ConditionsGroup = request.getParameter("ConditionsSelect");
            if(ConditionsGroup.equals("0")){
                strSQL += "and (CustName = '"+Conditions+"' or Mobile = '"+Conditions+"')";
            }else if(ConditionsGroup.equals("1")){
                strSQL += "and (CustName = '"+Conditions+"')";
            }else if(ConditionsGroup.equals("2")){
                strSQL += "and (Mobile = '"+Conditions+"')";
            }
        }
        /*按钮查询*/
        String QueryName = request.getParameter("buttonType");
        if (QueryName.equals("division")) {/*已分配*/
            strSQL += " and (WorknumOwner in (select worknum from dddWork) and  WorknumOwner is not null)";
        } else if (QueryName.equals("notDivision")) {/*未分配*/
            strSQL += " and (WorknumOwner not in (select worknum from dddWork) or  WorknumOwner is null) ";
        }
        return strSQL;
    }

    /*获取导入数据的导入ID，查询条件*/
    public String setStrSqlType(HttpServletRequest request,String StrSqlOther){
        String strSQL = setStrSql(request, StrSqlOther);
        String tableFormType = request.getParameter("tableFormType");
        if(Objects.equals(tableFormType, "import")){
            String importId = request.getParameter("importId");
            strSQL += " and importId='"+importId+"' ";
        }
        System.out.println("查询条件："+strSQL);
        return strSQL;
    }

    /*设置公共池查询基础查询语句*/
    public String getImportBasisSql(){
        return setCustBasisSql();
    }

    /*设置公共池数据表头(除去下拉框)信息*/
    public String[] getImportList(){
        String[] otherList = getCustList();
        return otherList;
    }

    /*设置公共池数据下拉框表头信息*/
    public String[] getImportSelectList(){
        String[] selectList = getCustSelectList();
        return selectList;
    }

    /*设置客户管理分页数据坐席表头信息*/
    public String[] getImportWorkList(){
        String[] selectList = getCustWorkList();
        return selectList;
    }

    /*设置客户管理分页数据工作组表头信息*/
    public String[] getImportGroupList(){
        String[] selectList = getCustGroupList();
        return selectList;
    }

    /*设置公共池数据中下拉框信息的联表查询*/
    public String getImportSelectSql(String sql){
        return getCustSelectSqlNoPic(sql);
    }

    /*导入资料查询*/
    public Object importQuery(HttpServletRequest request){
        /*获取查询条件*/
//        String strSql = setStrSql(request, setImportStrSql(request))+" and (WorknumOwner not in (select worknum from dddWork) or  WorknumOwner is null) ";
        String strSql = setStrSql(request, setImportStrSql(request));
        strSql = setStrSqlType(request,strSql);
        /*基础信息查询表*/
        String dataTable = getImportBasisSql();
        /*初始化页面*/
        init(request, dataTable, strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "custid", "dtUpdate");
        /*添加下拉框的联表查询*/
        sql = getImportSelectSql(sql);
        System.out.println("查询公共池："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        /*添加动态*/
        ArrayList<String> list = stringlistToArray(getCustList());
        list.add("custDynamicInfo");
        /*添加工作组以及归属坐席*/
        list.add("worknumOwner");
        list.add("groupid");
//        list.add("picName");
        json.setDataList(sqlListData(sql, addList(getCustSelectList(), arrayToStringlist(list)), "cust"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("import" + "ToExcel", importExcel(strSql));
        json.setSqlTable("import");
        return json;
    }

    /*获取公共池查询的导出Excel语句*/
    public String importExcel(String str){
        Query query = new Query();
        /*分页查询中导出excel语句*/
        String sql = query.setExcelSql(getImportBasisSql(), str, "dtUpdate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getImportSelectSql(sql);
        return sql;
    }

    /*查询所有导入批号*/
    public Object getImportID(){
        String sql = "select ImportID from px_crm_cust group by ImportID";
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list = {"importID"};
        json.setDataList(sqlListData(sql, list, "import"));
        return json;
    }

    /*资源管理的分配操作*/
    public boolean updataDivis_1(HttpServletRequest request){
        boolean f = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String newTime = format.format(new Date());
        String[] worknumList = request.getParameter("worknumList").split(",");//待分配员工工号list
        String[] valueList = request.getParameter("valueList").split(",");//员工对应分配的数量list
        String advancedQuery = request.getParameter("advancedQuery");//待分配的批号
        if(advancedQuery==null|| Objects.equals(advancedQuery, "null")){
            advancedQuery = " ";
        }
        System.out.println("advancedQuery1=" + advancedQuery);
        for(int i=0;i<worknumList.length;i++){
            String sql = "update px_CRM_Cust set dtUpdate='"+newTime+"', dtGive='"+newTime+"',WorknumOwner='"+worknumList[i]+"' where Custid in " +
                    "(select  cd.Custid from (select custid from px_crm_cust limit 0,"+valueList[i]+") as cd )" +
                    " and importId in ("+advancedQuery+") and WorknumOwner not in (select worknum from dddwork)";
            System.out.println("资源管理分配数据语句"+i+":"+sql);
            f = f&&updataSql(sql);
        }
        return f;
    }

    /*资源管理所属批号数据的分配操作*/
    public boolean updataDivis_2(HttpServletRequest request){
        boolean f = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String newTime = format.format(new Date());
        String[] worknumList = request.getParameter("worknumList").split(",");//待分配员工工号list
        String[] valueList = request.getParameter("valueList").split(",");//员工对应分配的数量list
        String advancedQuery = request.getParameter("advancedQuery");//待分配的批号
        if(advancedQuery==null|| Objects.equals(advancedQuery, "null")){
            advancedQuery = " ";
        }
        System.out.println("advancedQuery1="+advancedQuery);
        for(int i=0;i<worknumList.length;i++){
            String sql = "update px_CRM_Cust set dtUpdate='"+newTime+"', dtGive='"+newTime+"',WorknumOwner='"+worknumList[i]+"' where Custid in " +
                    "(select  ab.Custid from(select  Custid from px_CRM_Cust where 1=1 limit 0," +valueList[i]+")as ab)"+
                    " and Custid in ("+advancedQuery+") ";
            System.out.println("资源管理所属批号数据的分配数据语句"+i+":"+sql);
            f = f&&updataSql(sql);
        }
        return f;
    }
}
