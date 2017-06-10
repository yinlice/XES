package Wxzt.servlet.Common;

import Wxzt.tool.IniReader;
import Wxzt.tool.JDBC;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/1/12.
 */
public class getData_worker{
    public String recordUrl = "";
    public String outsideUrl = "";
    public String ip = "";
    public String imgPath = "";
    public void setFile() throws IOException {
        IniReader reader = new IniReader();
        recordUrl = reader.getValue("Record", "url");
        outsideUrl = reader.getValue("outside","URL");
        ip = reader.getValue("outside","IP");
        imgPath = reader.getValue("Img", "url");
    }
    public ArrayList<ArrayList> getList(String sql) throws Exception {
        ResultSet os;//记录总数据数量的ResultSet
        JDBC jdbc = new JDBC();
        ArrayList<ArrayList> worker = new ArrayList<ArrayList>();
        try {
            os = jdbc.executeQuery(sql);
            try {
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("workname"));//0姓名
                    data.add(os.getString("worknum"));//1工号
                    data.add(os.getString("sipnum"));//2分机号
                    data.add(os.getString("Groupid"));//3部门ID
                    data.add(os.getString("roleid"));//4角色
                    data.add(changeMean(os.getString("QXLB"),os.getString("QXLB1")));//5菜单列表
                    data.add(os.getString("IsMgr"));//6范围
                    data.add(os.getString("workid"));//7ID
                    data.add(recordUrl);//8录音文件URL
                    data.add(os.getString("PassNumber"));//9-外显号码
                    data.add(outsideUrl);//10-点击外呼URL
                    data.add(os.getString("GroupName"));//11-工作组
                    data.add(os.getString("picName"));//12-坐席头像
                    data.add(ip);//13-工具条所需IP
                    data.add(os.getString("groupnum"));//14-工作组编号
                    data.add(imgPath+"/");//15-头像路径
                    data.add(os.getString("crm"));//16-crm名称
                    worker.add(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return worker;
    }


    /**
     * 修改权限勾选的菜单信息,将坐席和组别的信息重合
     * @param userMean 坐席权限的菜单信息
     * @param groupMean 组别权限的菜单信息
     * */
    public String changeMean(String userMean,String groupMean){
        if(userMean==null||Objects.equals(userMean, "")){
            return groupMean;
        }
        if(groupMean==null||Objects.equals(groupMean, "")){
            return userMean;
        }
//        String[] user = userMean.split(",");
//        String[] group = groupMean.split(",");

//        List<String> userArr = new ArrayList(Arrays.asList(user));
//        List<String> groupArr = new ArrayList(Arrays.asList(group));

        /*获取组别权限独有项*/
//        groupArr.removeAll(userArr);


//        return listToString(userArr,",")+","+listToString(groupArr,",");
        return userMean+","+userMean;
    }

    public String listToString(List<String> list,String join){
        StringBuffer sb = new StringBuffer();
        for(int i = 0,l = list.size(); i < l; i++){
            if(i==l-1){
                sb.append(list.get(i));
            }else {
                sb.append(list.get(i)).append(join);
            }
        }
        String s = sb.toString();
        System.out.println("菜单信息："+s);
        return s;
    }


    public String getSql(){
        return "select workid,workname,worknum,sipnum,a.Groupid,c.groupnum,d.picName,roleid,b.QXLB,e.QXLB as QXLB1,IFNULL(b.IsMgr,e.IsMgr) as IsMgr,PassNumber,c.GroupName,c.crm from dddWork as a\n" +
                "left join px_CRM_JSQX as b on a.roleid = b.JSBH \n" +
                "left join dddGroup as c on a.Groupid = c.Groupid\n" +
                "left join px_CRM_JSQX as e on c.role = e.JSBH \n" +
                "left join dddPic as d on a.picId = d.picId";
    }
    public ArrayList<ArrayList> load(HttpServletRequest request) throws Exception {
        setFile();
        //查询条件
        String pw = request.getParameter("pw");
        String UserName = request.getParameter("UserName");
        String sqlWhere = "",sql = "";
        if(pw!=null){
            sqlWhere += " and password='"+pw+"'";
        }
        sqlWhere += " and worknum='"+UserName+"'";
        sql = getSql()+ " where 1=1 "+sqlWhere;
        System.out.println("坐席登录语句：" + sql);
        return getList(sql);
    }
    public ArrayList<ArrayList> getDict(String workNum) throws Exception {
        setFile();
        String sql = getSql() + " where worknum="+workNum;//查询语句
        System.out.println("获取坐席信息语句：" + sql);
        return getList(sql);
    }
}
