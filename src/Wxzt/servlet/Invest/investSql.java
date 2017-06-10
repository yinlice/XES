package Wxzt.servlet.Invest;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016-5-11.
 */
public class investSql extends Query{
    /*客户投资记录的查询条件，必填写*/
    public String setInvStrSql(HttpServletRequest request){
        String strSQL = "";
        /*客户id*/
        String custid = request.getParameter("custid");
        if(custid!=null){
            strSQL+=" and custid='"+custid+"'";
        }
        return strSQL;
    }

    /*设置客户投资记录分页查询基础查询语句*/
    public String getInvBasisSql(){
        return " px_CRM_CustInvest ";
    }

    /*设置客户投资记录表头(除去下拉框)信息*/
    public String[] getInvList(){
        String[] otherList = {"custid","investAmount","investTime","id"};
        return otherList;
    }

    /*设置客户投资记录坐席表头信息*/
    public String[] getInvWorkList(){
        String[] selectList = {"worknum"};
        return selectList;
    }

    /*设置客户录音数据中下拉框信息的联表查询*/
    public String getInvSelectSql(String sql){
        String[] list = {};
        setSqlSelect_T("a", list, getInvList(), getInvWorkList(), list, "");
        return "select b.custName,"+sqlHeader+" from ("+sql+") as a "+sqlJoin+
                " left join px_crm_cust as b on a.custid = b.custid";
    }

    /*客户投资记录分页查询*/
    public Object investQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setInvStrSql(request));
        /*基础信息查询表*/
        String dataTable = getInvBasisSql();
        /*初始化页面，设置页面的相关信息*/
        init(request, dataTable, strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "InvestTime");
        /*分页数据中下拉框信息的联表查询*/
        sql = getInvSelectSql(sql);
        System.out.println("查询投资记录："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        /*将客户id替换为客户名称*/
        String[] otherList = getInvList();
        otherList[0] = "custName";
        json.setDataList(sqlListData(sql, addList(otherList, getInvWorkList()), "invest"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        return json;
    }

    /*投资记录删除*/
    public boolean investDel(HttpServletRequest request){
        String id = request.getParameter("id");
        String sql = "delete from px_CRM_CustInvest where ID="+id;
        return updataSql(sql);
    }

    /*投资记录添加*/
    public boolean investInsert(HttpServletRequest request){
        String custid = request.getParameter("id");
        String InvestAmount = request.getParameter("investAmount");//投资金额
        String todayTime = request.getParameter("todayTime");
        String worknumOwner = request.getParameter("worknumOwner");
        String sql = "INSERT  into px_CRM_CustInvest (Custid,InvestAmount,InvestTime,worknum) " +
                "values ('"+custid+"','"+InvestAmount+"','"+todayTime+"','"+worknumOwner+"')";
        return updataSql(sql);
    }
}
