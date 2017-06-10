package Wxzt.servlet.Common;

import Wxzt.servlet.Cdr.cdrBean;
import Wxzt.servlet.Common.table.tableHeadBean;
import Wxzt.servlet.Config.configBean;
import Wxzt.servlet.Cust.custBean;
import Wxzt.servlet.Cust.custHeadBean;
import Wxzt.servlet.Cust.repeatCustBean;
import Wxzt.servlet.CustHead.custHeadBean_tp;
import Wxzt.servlet.GroupCall.gci.gciBean;
import Wxzt.servlet.GroupCall.groupCallBean;
import Wxzt.servlet.Import.importBean;
import Wxzt.servlet.Import.stationBean;
import Wxzt.servlet.Invest.investBean;
import Wxzt.servlet.Reminder.ReminderBean;
import Wxzt.servlet.RepairOrder.repairBean;
import Wxzt.servlet.RepairOrderStation.repairStaBean;
import Wxzt.servlet.SMS.Model.SmsModelBean;
import Wxzt.servlet.SMS.SmsBean;
import Wxzt.servlet.Setting.user.company.companyUserBean;
import Wxzt.servlet.Visit.visitBean;
import Wxzt.servlet.dynamic.dynamicBean;
import Wxzt.servlet.logreminder.LogreminderBean;
import Wxzt.tool.IniReader;
import Wxzt.tool.JDBC;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-5.
 */
public class Query {
    public String sqlHeader;//sql表头内容
    public String sqlJoin;//sql中下拉框的联表查询

    public int currentPage;//当前页数
    public int currentNum;//每页显示数量
    public int count;//数据总量
    public int countPage;//总页数

    /*获取config文件中的参数*/
    public String getConfig(String configType,String configName){
        File f = new File(this.getClass().getResource("/").getPath());
        String urlINI = f+"\\conf\\sysconf.ini";
        try {
            IniReader reader = new IniReader(urlINI);
            return reader.getValue(configType,configName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*初始化页面参数*/
    public void init(HttpServletRequest request,String DataTable,String strSQL){
        /*获取当前页数*/
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        /*获取每页显示数据量*/
        currentNum = Integer.parseInt(request.getParameter("currentNum"));
        /*获取数据总量*/
        String sqlCount = "select count(*) from "+DataTable+" where 1=1 "+strSQL;
        System.out.println("查询总数据库："+sqlCount);
        String countString = sqlOneData(sqlCount);
        count = Integer.parseInt(countString==null?"0":countString);
        /*获取总页数*/
        if (count%currentNum==0) {
            countPage = count/currentNum;
        }else {
            countPage = count/currentNum+1;
        }
    }

    /*获取查询条件*/
    public String setStrSql(HttpServletRequest request,String StrSqlOther){
        String AdvancedQuery = request.getParameter("otherQueryCondition");//高级查询
        if(AdvancedQuery!=null&& !Objects.equals(AdvancedQuery, "")){//存在高级查询内容
            return  " and "+AdvancedQuery;
        }else {
            return StrSqlOther;//显示在不同页面上的查询条件
        }
    }


    /*设置查询分页数据的语句*/
    public String setSql(String table,String strSQL,String id,String order){
        int currentCount = currentNum * (currentPage-1);//已显示的数据
        return  "SELECT  *  FROM " +table+
                " WHERE 1=1  "+strSQL+" ORDER BY "+order+" DESC limit "+currentCount+","+currentNum;
    }

    /*设置查询分页数据的语句*/
    public String setSqlOrDesc(String table,String strSQL,String id,String order){
        int currentCount = currentNum * (currentPage-1);//已显示的数据
        return  "SELECT * FROM " +table+
                " WHERE 1=1  "+strSQL+" ORDER BY "+order+" limit "+currentCount+","+currentNum ;
    }

    /*设置单条数据的语句*/
    public String setOneSql(String table,String strSQL){
        return "select * from "+table+" where 1=1 "+strSQL;
    }

    /*设置非分页的多条数据的语句*/
    public String setSqlNoPage(String table,String strSQL,String order){
        return "select * from "+table+" where 1=1 "+strSQL+" ORDER BY "+order+" DESC";
    }
    /*设置非分页的多条数据的语句*/
    public String setSqlNoPage_1(String table,String strSQL,String order){
        return "select * from "+table+" where 1=1 "+strSQL+order;
    }

    /*下拉框的联表查询语句的初始化操作*/
    public void setSqlSelect(String tablename,String[] select,String[] other,String dictName){
        String headerSelect = "",sqlLeftJoin = "",header = "";
        for(int j = 0;j<other.length;j++){
            header += " "+tablename+"."+other[j];
            if(j!=other.length-1){
                header += ",";
            }
        }
        sqlHeader = header;
        int selectLength = select.length;
        if(selectLength>0){
            for(int i=0;i<selectLength;i++){
                String joinTableName = tablename+i;//联表查询的指代名称
                headerSelect += joinTableName+".DictMean as "+select[i];
                if(i!=selectLength-1){
                    headerSelect += ",";
                }
//            "left join v_Dict as f on 'px_CRM_Cust_Sex'+cast(a.Sex as varchar) = f.DictType+f.DictCode"
                sqlLeftJoin += " left join v_Dict as "+joinTableName+" on concat(" +
                        "'"+dictName+"_"+select[i]+"',"+tablename+"."+select[i]+" )= concat("+joinTableName+".DictType,"+joinTableName+".DictCode ) ";
            }
            sqlHeader += ","+headerSelect;
        }
        sqlJoin = sqlLeftJoin;
    }

    /*下拉框的联表查询语句的初始化操作,客户以及工作组单独设置*/
    public void setSqlSelect_T(String tablename,String[] select,String[] other,String[] work,String[] group,String dictName){
        String headerSelect = "",headerWork = "",headerGroup = "",sqlLeftJoin = "",header = "";
        for(int j = 0;j<other.length;j++){
            header += " "+tablename+"."+other[j];
            if(j!=other.length-1){
                header += ",";
            }
        }
        sqlHeader = header;
        /*设置一般的下拉框*/
        int selectLength = select.length;
        if(selectLength>0){
            for(int m=0;m<selectLength;m++){
                String joinTableName = tablename+m;//联表查询的指代名称
                headerSelect += joinTableName+".DictMean as "+select[m];
                if(m!=selectLength-1){
                    headerSelect += ",";
                }
//            "left join v_Dict as f on 'px_CRM_Cust_Sex'+cast(a.Sex as varchar) = f.DictType+f.DictCode"
                sqlLeftJoin += " left join v_Dict as "+joinTableName+" on concat(" +
                        "'"+dictName+"_"+select[m]+"',"+tablename+"."+select[m]+" )= concat("+joinTableName+".DictType,"+joinTableName+".DictCode )";
            }
            sqlHeader += ","+headerSelect;
        }
        /*设置坐席信息*/
        int workLength = work.length;
        if(workLength>0){
            for(int i=0;i<workLength;i++){
                String joinTableName = tablename+"_"+i;//联表查询的指代名称
                headerWork += joinTableName+".DictMean as "+work[i];
                if(i!=workLength-1){
                    headerWork += ",";
                }
//            "left join v_Dict as f on 'px_CRM_Cust_Sex'+cast(a.Sex as varchar) = f.DictType+f.DictCode"
                sqlLeftJoin += " left join v_Dict as "+joinTableName+" on " +
                        "concat ('common_workName',"+tablename+"."+work[i]+") = concat("+joinTableName+".DictType,"+joinTableName+".DictCode )";
            }
            sqlHeader += ","+headerWork;
        }
        /*设置工作组信息*/
        int groupLength = group.length;
        if(groupLength>0){
            for(int j=0;j<groupLength;j++){
                String joinTableName = tablename+"__"+j;//联表查询的指代名称
                headerGroup += joinTableName+".DictMean as "+group[j];
                if(j!=groupLength-1){
                    headerGroup += ",";
                }
//            "left join v_Dict as f on 'px_CRM_Cust_Sex'+cast(a.Sex as varchar) = f.DictType+f.DictCode"
                sqlLeftJoin += " left join v_Dict as "+joinTableName+" on concat( " +
                        "'common_groupName',"+tablename+"."+group[j]+" )= concat("+joinTableName+".DictType,"+joinTableName+".DictCode )";
            }
            sqlHeader += ","+headerGroup;
        }
        sqlJoin = sqlLeftJoin;
    }

    /*下拉框的联表查询语句的初始化操作,客户以及工作组，角色单独设置*/
    public void setSqlSelect_R(String tablename,String[] select,String[] other,String[] work,String[] group,String[] role,String dictName){
        setSqlSelect_T(tablename,select,other,work,group,dictName);
        String headerGroup = "",sqlLeftJoin = "";
        /*设置角色信息*/
        int roleLength = role.length;
        if(roleLength>0){
            for(int j=0;j<roleLength;j++){
                String joinTableName = tablename+"bcd___"+j;//联表查询的指代名称
                headerGroup += joinTableName+".DictMean as "+role[j];
                if(j!=roleLength-1){
                    headerGroup += ",";
                }
                sqlLeftJoin += " left join v_Dict as "+joinTableName+" on " +
                        "concat('common_roleName',"+tablename+"."+role[j]+" )=concat("+joinTableName+".DictType,"+joinTableName+".DictCode)";
            }
            sqlHeader += ","+headerGroup;
        }
        sqlJoin += sqlLeftJoin;
    }
    /*设置单条更新语句的set内容*/
    public String setOneUpdate(HttpServletRequest request,ArrayList<String> setList){
        String set = "";
        for(int i=0;i<setList.size();i++){
            String setName = setList.get(i);
            String setValue = request.getParameter(setName);
            if(setValue==null||setValue.equals("")){
                continue;
            }
            set += setName+"='"+setValue+"',";
        }
        return "set "+set.substring(0,set.length()-1);
    }
    /*设置多条语句更新*/
    public String setMoreUpdate(HttpServletRequest request,String toUpdate,String setListName,ArrayList<String> setList){
        String set = " set "+toUpdate+" = case "+setListName;
        for(int i=0;i<setList.size();i++){
            String setName = setList.get(i);
            String setValue = request.getParameter(setName);
            set += " when '"+setName+"' then '"+setValue+"' ";
        }
        return set+" end";
    }

    /*设置单条添加语句的value以及name,添加其他字段*/
    public String setOneInsert(HttpServletRequest request,String[] nameList,String[] addName,String[] addValue){
        String name = "",value = "",nameByAdd = ",",valueByAdd = ",";
        /*根据数组获取待添加的数据,未传递的参数不添加*/
        for(int i=0;i<nameList.length;i++){
            String oneName = nameList[i];
            String oneValue = request.getParameter(oneName);
            if(oneValue==null||oneValue.equals("")){
                continue;
            }
            name += oneName+",";
            value += "'"+oneValue+"',";
        }
        for(int j=0;j<addName.length;j++){
            nameByAdd += addName[j]+",";
            valueByAdd += "'"+addValue[j]+"',";
        }
        String insertValue = "("+name.substring(0,name.length()-1)+" "+nameByAdd.substring(0,nameByAdd.length()-1)+") values " +
                "("+value.substring(0,value.length()-1)+" "+valueByAdd.substring(0,valueByAdd.length()-1)+")";
        System.out.println("insertValue="+insertValue);
        return insertValue;
    }

    /*设置单条添加语句的value以及name，没有添加其他字段*/
    public String setOneInsertNoadd(HttpServletRequest request,String[] nameList){
        String name = "",value = "";
        for(int i=0;i<nameList.length;i++){
            String oneName = nameList[i];
            String oneValue = request.getParameter(oneName);
            if(oneValue==null){
                continue;
            }
            name += oneName+",";
            value += "'"+oneValue+"',";
        }
        String insertValue = "("+name.substring(0,name.length()-1)+") values " +
                "("+value.substring(0,value.length()-1)+")";
        System.out.println("setOneInsertNoadd="+insertValue);
        return insertValue;
    }

    /*设置导出excel的语句*/
    public String setExcelSql(String table,String strSQL,String order){
        String currentCount = getConfig("download","ExcelMax")==null?"10000":getConfig("download","ExcelMax");
        return  "SELECT  * FROM " +table+ " WHERE 1=1 "+strSQL+" ORDER BY "+order+" DESC limit 0,"+currentCount;
    }

    /*获取单个数据*/
    public String sqlOneData(String sql) {
        try {
            JDBC jdbc = new JDBC();
            ResultSet osCount = jdbc.executeQuery(sql);
            if (osCount.next()) {
                return osCount.getString(1);
            }else {
                return "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*获取分页对象*/
    public Object getTableObject(String tableType){
        if(Objects.equals(tableType, "cust")){/*客户*/
            return new custBean();
        }else if(Objects.equals(tableType, "custHeadTP")){/*来电弹屏顶部信息*/
            return new custHeadBean_tp();
        }else if(Objects.equals(tableType, "custRepeat")){/*排重客户数据*/
            return new repeatCustBean();
        }else if(Objects.equals(tableType, "custHead")){/*动态*/
            return new custHeadBean();
        }else if(Objects.equals(tableType, "dyn")){/*动态*/
            return new dynamicBean();
        }else if(Objects.equals(tableType, "cdr")){/*录音*/
            return new cdrBean();
        }else if(Objects.equals(tableType, "invest")){/*投资记录*/
            return new investBean();
        }else if(Objects.equals(tableType, "visit")){/*回访记录*/
            return new visitBean();
        }else if(Objects.equals(tableType, "import")){/*公共池*/
            return new importBean();
        }else if(Objects.equals(tableType, "station")){/*导入批次*/
            return new stationBean();
        }else if(Objects.equals(tableType, "repair")){/*工单*/
            return new repairBean();
        }else if(Objects.equals(tableType, "repairSta")){/*工单处理过程*/
            return new repairStaBean();
        }else if(Objects.equals(tableType, "sms")){/*短信查询*/
            return new SmsBean();
        }else if(Objects.equals(tableType, "smsModel")){/*短信模板查询*/
            return new SmsModelBean();
        }else if(Objects.equals(tableType, "config")){/*参数配置*/
            return new configBean();
        }else if(Objects.equals(tableType, "comUser")){/*公司员工信息*/
            return new companyUserBean();
        }else if(Objects.equals(tableType, "tableHead")){/*table表头信息*/
            return new tableHeadBean();
        }else if(Objects.equals(tableType, "groupCall")){/*群呼*/
            return new groupCallBean();
        }else if(Objects.equals(tableType, "groupCallImport")){/*群呼情况*/
            return new gciBean();
        }else if(Objects.equals(tableType, "QueryReminder")){
            return new ReminderBean();
        }else if(Objects.equals(tableType, "QueryLogreminder")){
            return new LogreminderBean();
        }else {
            return new custBean();
        }
    }

    /*获取多个数据并返回报错*/
    public ArrayList<Object> sqlListDataError(String sql,String[] dataName,String tableType) throws SQLException{
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            JDBC jdbc = new JDBC();
            ResultSet os = jdbc.executeQuery(sql);
            while (os.next()) {
                Object data = this.getTableObject(tableType);
                for(int i = 0;i<dataName.length;i++){
//                    String oneData = os.getString(dataName[i])==null?"":os.getString(dataName[i]);
                    BeanUtils.setProperty(data, dataName[i], os.getString(dataName[i]));
                }
                list.add(data);
            }
            jdbc.closeConnection();
            return list;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*获取多个数据*/
    public ArrayList<Object> sqlListData(String sql,String[] dataName,String tableType) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            JDBC jdbc = new JDBC();
            ResultSet os = jdbc.executeQuery(sql);
            while (os.next()) {
                Object data = this.getTableObject(tableType);
                for(int i = 0;i<dataName.length;i++){
//                    String oneData = os.getString(dataName[i])==null?"":os.getString(dataName[i]);
                    BeanUtils.setProperty(data, dataName[i], os.getString(dataName[i]));
                }
                list.add(data);
            }
            jdbc.closeConnection();
            return list;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*语句的更新、添加、删除等操作*/
    public boolean updataSql(String sql){
        try {
            JDBC jdbc = new JDBC();
            boolean s = jdbc.executeUpdate(sql);
            jdbc.closeConnection();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*合并数组*/
    public String[] addList(String[] a,String[] b){
        String[] temp = new String[a.length+b.length];
        for(int i=0;i<a.length;i++){
            temp[i]=a[i];
        }
        for(int i=0;i<b.length;i++){
            temp[a.length+i]=b[i];
        }
        return temp;
    }

    /*合并数组*/
    public ArrayList<String> addListArray(String[] a,String[] b){
        ArrayList<String> temp = new ArrayList<String>();
        for(int i=0;i<a.length;i++){
            temp.add(a[i]);
        }
        for(int i=0;i<b.length;i++){
            temp.add(b[i]);
        }
        return temp;
    }

    /*ArrayList数组转化为string[]*/
    public String[] arrayToStringlist(ArrayList<String> list){
        int length = list.size();
        String[] stringList = new String[length];
        for(int i = 0;i<length;i++){
            stringList[i] = list.get(i);
        }
        return stringList;
    }

    /*string[]数组转化为ArrayList*/
    public ArrayList<String> stringlistToArray(String[] list){
        ArrayList<String> stringList = new ArrayList<String>();
        for(int i = 0;i<list.length;i++){
            stringList.add(list[i]);
        }
        return stringList;
    }

    /*string[]转化为字符串*/
    public String join(String[] list,String join){
        int length = list.length;
        if(length>0){
            String s = "";
            for (int i = 0;i<length;i++){
                if(i==length-1){
                    s += list[i];
                }else {
                    s += list[i]+join;
                }
            }
            return s;
        }else {
            return "";
        }
    }

    /*ArrayList转化为字符串*/
    public String join(ArrayList list,String join){
        int length = list.size();
        if(length>0){
            String s = "";
            for (int i = 0;i<length;i++){
                if(i==length-1){
                    s += list.get(i);
                }else {
                    s += list.get(i)+join;
                }
            }
            return s;
        }else {
            return "";
        }
    }

    /*返回前端传递的表头字段或者java内存中的字段*/
    public String[] ajaxOrJavaStringList(String ajaxList,String[] list){
        if(ajaxList!=null&& !Objects.equals(ajaxList, "")){
            return ajaxList.split(",");
        }else {
            return list;
        }
    }
}
