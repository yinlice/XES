package Wxzt.servlet.table.web;

import Wxzt.servlet.bean.QueryJsonData;
import Wxzt.servlet.bean.QueryTableJson;
import Wxzt.servlet.bean.background.User;
import Wxzt.tool.JDBC;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/5.
 */
public class getCust extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        String sql ;//查询语句
        ResultSet os;//记录总数据数量的ResultSet

        //获取jQuery传递的参数
        String Tel = request.getParameter("Mobile");
        sql = "select a.*,b.workname,c.GroupName from px_CRM_Cust as a\n" +
                "left join dddWork as b on a.WorknumOwner = b.worknum\n" +
                "left join dddGroup as c on b.Groupid = c.Groupid\n" +
                "where Mobile='"+Tel+"' or Telnum ='"+Tel+"'";
        System.out.println("SQLstate="+sql);

        JDBC jdbc = new JDBC();
        //查询表单数据数量
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        try {
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("Custid"));//ID0
                    data.add(os.getString("CustNum"));//用户编号
                    data.add(os.getString("CustName"));//用户姓名
                    data.add(os.getString("Sex"));//性别
                    data.add(os.getString("Mobile"));//联系电话
                    data.add(os.getString("ID_num"));//身份证号
                    data.add(os.getString("Telnum"));//固定电话
                    data.add(os.getString("QQ"));//QQ
                    data.add(os.getString("Job"));//职位
                    data.add(os.getString("Weixiin"));//微信
                    data.add(os.getString("Email"));//Email

                    data.add(os.getString("HomeAddr"));//家庭地址
                    data.add(os.getString("Addr"));//公司地址
                    data.add(os.getString("GroupName"));//归属小组
                    data.add(os.getString("workname"));//归属人
                    data.add(os.getString("dtVisit"));//回访时间

                    data.add(os.getString("IsRegister"));//组册
                    data.add(os.getString("IsTopUp"));//充值
                    data.add(os.getString("IsInvest"));//投资

                    data.add(os.getString("dtInvest"));//投资时间
                    data.add(os.getString("TopUpAmount"));//充值金额

                    data.add(os.getString("CustType"));//结束码
                    data.add(os.getString("CustGrade"));//跟进级别
                    data.add(os.getString("CallSituation"));//呼叫情况

                    data.add(os.getString("CustFrom"));//客户来源
                    data.add(os.getString("underly"));//标的
                    data.add(os.getString("birthday"));//生日
                    data.add(os.getString("Loan"));//贷款
                    data.add(os.getString("Marriage"));//婚姻
                    data.add(os.getString("WorkNature"));//工作性质

                    data.add(os.getString("CustNote"));//备注
                    listData.add(data);
                }
                jsonData.setDataList(listData);
                jsonData.setDataType("Y");
                System.out.println("ok");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}

