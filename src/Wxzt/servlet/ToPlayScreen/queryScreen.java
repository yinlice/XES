package Wxzt.servlet.ToPlayScreen;

import Wxzt.servlet.Common.getData_Dict;
import Wxzt.servlet.Common.getData_worker;
import Wxzt.servlet.Cust.custSql;
import Wxzt.servlet.dynamic.dynamicSql;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016/1/12.
 */
public class queryScreen extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("---------queryScreen");
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();
        screenBean to = new screenBean();
        String action = request.getParameter("action");
        /*action:0，获取弹屏主要信息；1，获取弹屏的tab中的沟通信息;2,更新动态；3，添加动态；4获取弹屏客户表头信息*/
        if(Objects.equals(action, "0")){
            String Mobile = request.getParameter("mobile");//电话号码
            String DictType = request.getParameter("DictType");//字典范围
            String workNum = request.getParameter("workNum");//坐席工号
            String crmType = request.getParameter("crmType");//获取客户类型
            if("cust".equals(crmType)){
                  /*数据获取*/
                custSql cust = new custSql();
                to.setDataList(cust.custOneQuery(request));/*获取客户数据*/
            }else if("doctor".equals(crmType)){
                System.out.println("+++++++++++++++++获取医生的弹屏数据获取医生的弹屏数据获取医生的弹屏数据获取医生的弹屏数据获取医生的弹屏数据+++++++++++++");
                Wxzt.servlet.Doctor.custSql sql = new Wxzt.servlet.Doctor.custSql();
                to.setDataList(sql.custOneQuery(request));/*获取医生数据*/
            }
            if(DictType!=null){
                getData_Dict dict = new getData_Dict();
                to.setDictList(dict.getDict(DictType));/*获取字典项*/
            }else {
                System.out.println("不需要查询字典");
            }
            if(workNum!=null){
                getData_worker worker = new getData_worker();
                try{
                    to.setWorkerList(worker.getDict(workNum));/*获取坐席数据*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("不需要查询客户信息");
            }
        }else if(Objects.equals(action, "1")) {
            /*获取动态数据*/
            dynamicSql dyn = new dynamicSql();
            to.setTabData(dyn.dynQuery(request));
        }else if(Objects.equals(action, "2")) {
            /*更新动态数据*/
            dynamicSql dyn = new dynamicSql();
            to.setDynamicResult(dyn.dynUpdata_2(request));
            /*更新客户数据*/
            custSql custsql = new custSql();
            custsql.updateCust(request);
        }else if(Objects.equals(action, "3")) {
            /*添加动态数据*/
            dynamicSql dyn = new dynamicSql();
            to.setDynamicResult(dyn.dynUpdata(request));
            /*更新客户数据*/
            custSql custsql = new custSql();
            custsql.updateCust(request);
        }else if(Objects.equals(action, "4")) {
            custSql c = new custSql();
            to.setTabData(c.queryScreenHead(request));
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(to);
        System.out.println(json);
        out.println(json);
    }
}
