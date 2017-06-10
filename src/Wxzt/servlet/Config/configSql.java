package Wxzt.servlet.Config;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016-6-23.
 */
public class configSql extends Query {
    /*设置参数配置数据表头(除去下拉框)信息*/
    public String[] getConfigList(){
        String[] otherList = {"config","value"};
        return otherList;
    }
    /*获取参数配置数据*/
    public Object configOneQuery(){
        /*基础信息查询表*/
        String sql = "select config,value from dddConfig";
        System.out.println("参数配置数据语句：" + sql);
        /*获取参数配置数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, getConfigList(), "config"));
        return json;
    }
    /*参数配置更新语句*/
    public boolean updateConfig(HttpServletRequest request){
        String[] List = {"toolbarIP","cmdport","statport","monitorport","dataEncrypt","extract","notification"};/*多条同步更新的判断范围数组*/
        ArrayList<String> setList = stringlistToArray(List);
        String sql = "update dddConfig "+setMoreUpdate(request,"value","config",setList);
        System.out.println("参数配置更新语句：" + sql);
        return updataSql(sql);
    }
}
