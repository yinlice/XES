package Wxzt.servlet.Visit;

import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by Administrator on 2016-6-16.
 */
public class calendarSql extends visitSql {
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
        strSQL += " and worknumOwner = '"+worknum+"'";
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
        return strSQL+timeSQL+" and dtvisit !='' and dtvisit is not null";
    }
    /*设置客户回访表头(除去下拉框)信息*/
    public String[] getVisitList(){
        String[] otherList = {"custName","dtVisit","mobile"};
        return otherList;
    }
    /*客户管理分页查询*/
    public Object visitQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, getVisitStrSql(request));
        /*查询分页数据语句*/
        String sql = "select custname,mobile,dtVisit from px_CRM_Cust where 1=1 "+strSql+" ORDER BY dtVisit DESC";
        System.out.println("回访客户日历日程查询："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, getVisitList(), "visit"));
        return json;
    }
}
