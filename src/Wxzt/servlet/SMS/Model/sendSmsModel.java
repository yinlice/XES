package Wxzt.servlet.SMS.Model;

import Wxzt.servlet.Common.Query;
import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016-10-18.
 */
public class sendSmsModel extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------sendSmsModel");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println(sendSms(request));
    }

    /*获取短信模板*/
    public String getModelContent(HttpServletRequest request){
        String modelType = request.getParameter("modelType");
        String modelName = request.getParameter("modelName");
        String modelContent = "0";
        if(modelType!=null&&!Objects.equals(modelType, "")){
            String sqlModel = " select modelContent from px_CRM_CompanySMSModel where type='"+modelType+"' ";
            if(modelName!=null&&!Objects.equals(modelName, "")){
                sqlModel += " and name = '"+modelName+"'";
            }
            System.out.println("查询短信模板语句：" + sqlModel);
            Query que = new Query();
            modelContent = que.sqlOneData(sqlModel);
            System.out.println("短信模板语句：" + modelContent);
        }
        return modelContent;
    }

    /*获取待替换的字段数组*/
    public ArrayList<String> getModelList(String modelContent){
        Pattern pattern = Pattern.compile("(\\(.+?\\))");
        Matcher matcher = pattern.matcher(modelContent);
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        while (matcher.find(i)){
            list.add(matcher.group(1).replace("(", "").replace(")", ""));
            i = matcher.end();
        }
        return list;
    }

    /*获取待发送的短信信息*/
    public String getContent(HttpServletRequest request,String modelContent,ArrayList<String> list){
        String oneList = "",oneListStr = "";
        for(int i = 0;i<list.size();i++){
            oneList = list.get(i);
            oneListStr = request.getParameter(oneList);
            if(oneListStr!=null){
                modelContent = modelContent.replace("("+oneList+")",oneListStr);
            }
        }
        return modelContent;
    }

    /*获取收件人的相关信息*/
    public String getReceive(HttpServletRequest request,String action){
        String worknum = request.getParameter("worknum");
        String sql = "";
        Query que = new Query();
        if(Objects.equals(action, "0")){//短信
            sql = "select mobile1 from dddWork where worknum = "+worknum;
        }else if(Objects.equals(action, "1")){//邮件
            sql = "select email from dddWork where worknum = "+worknum;
        }
        System.out.println("查询收件人信息：" + sql);
        return que.sqlOneData(sql);
    }

    /*插入发送消息入库*/
    public boolean insertSms(HttpServletRequest request,String action,String receive,String content) throws IOException {
        String custnum = request.getParameter("custNum");
        String worknum = request.getParameter("worknum");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String sql = "insert into WX_SMS_waitSend (Mobile,smsContent,dt,custnum,worknum,smsType) values " +
                "('"+receive+"','"+content+"','"+formatter.format(new Date(System.currentTimeMillis()))+"','"+custnum+"','"+worknum+"',"+action+")";
        System.out.println("发送短信模板语句：" + sql);
//        JdbcForCti jdbc = new JdbcForCti();
        JDBC jdbc = new JDBC();
        boolean toBool = jdbc.executeUpdate(sql);
        System.out.println("发送短信模板：" + toBool);
        jdbc.closeConnection();
        return toBool;
    }

    /*发送短信*/
    public boolean sendSms(HttpServletRequest request) throws IOException {
        /*判断收件人信息是否完整*/
        String action = request.getParameter("action");
        String receive = getReceive(request,action);
        if(receive==null|| Objects.equals(receive, "0")){
            System.out.println("收件人信息为空");
            return false;
        }
        /*判断短信模板是否完整*/
        String modelContent = getModelContent(request);
        if(Objects.equals(modelContent, "0")){
            System.out.println("短信模板信息为空");
            return false;
        }
        /*获取待发送的主体信息*/
        ArrayList<String> list = getModelList(modelContent);
        String content = getContent(request, modelContent, list);

        return insertSms(request,action,receive,content);
    }
}