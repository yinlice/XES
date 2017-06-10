package Wxzt.servlet.ToPlayScreen.getData;

import Wxzt.tool.JDBC;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12.
 */
public class getData_Cust {
    public ArrayList<ArrayList> getCust(String Mobile) throws IOException {
        String mobile_1 = "0"+Mobile;//外地加0的号码
        String DataTable = "(SELECT m.*,m1.groupid,m2.groupName,\n" +
                "(m1.workName+'('+m1.worknum+')') as workName,m1.worknum  \n" +
                "FROM px_CRM_Cust as m\n" +
                "left join\n" +
                "dddWork as m1 on m.WorknumOwner=m1.worknum\n" +
                "left join\n" +
                "dddGroup as m2 on m1.groupid=m2.groupid) as cust";
        String sql = "select * from "+DataTable+" where  Mobile='"+Mobile+"' or Telnum ='"+Mobile+"' or Mobile='"+mobile_1+"' or Telnum ='"+mobile_1+"' order by Telnum,Mobile desc";//查询语句
        ResultSet os;//记录总数据数量的ResultSet
        JDBC jdbc = new JDBC();
        ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
        System.out.println("custSQL="+sql);
        try {
            os = jdbc.executeQuery(sql);
            try {
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("Custid"));//ID0
                    data.add(os.getString("CustNum"));//用户编号1
                    data.add(os.getString("CustName"));//用户姓名2
                    data.add(os.getString("Sex"));//性别3
                    data.add(os.getString("Mobile"));//联系电话4
                    data.add(os.getString("IDnum"));//身份证号5
                    data.add(os.getString("Telnum"));//固定电话6
                    data.add(os.getString("QQ"));//QQ7
                    data.add(os.getString("Job"));//职位8
                    data.add(os.getString("Weixiin"));//微信9
                    data.add(os.getString("Email"));//Email10

                    data.add(os.getString("HomeAddr"));//家庭地址11
                    data.add(os.getString("Addr"));//公司地址12
                    data.add(os.getString("Groupid"));//归属小组13
                    data.add(os.getString("WorknumOwner"));//归属人14
                    data.add(os.getString("dtVisit"));//回访时间15

                    data.add(os.getString("IsRegister"));//组册16
                    data.add(os.getString("IsTopUp"));//充值17
                    data.add(os.getString("IsInvest"));//投资18

                    data.add(os.getString("dtInvest"));//投资时间19
                    data.add(os.getString("TopUpAmount"));//充值金额20

                    data.add(os.getString("CustType"));//结束码21
                    data.add(os.getString("CustGrade"));//跟进级别22
                    data.add(os.getString("CallSituation"));//呼叫情况23

                    data.add(os.getString("CustFrom"));//客户来源24
                    data.add(os.getString("underly"));//标的25
                    data.add(os.getString("birthday"));//生日26
                    data.add(os.getString("Loan"));//贷款27
                    data.add(os.getString("Marriage"));//婚姻28
                    data.add(os.getString("WorkNature"));//工作性质29

                    data.add(os.getString("CustNote"));//备注30
                    listData.add(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return listData;
    }
}
