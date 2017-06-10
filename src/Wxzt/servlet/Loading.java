package Wxzt.servlet;

import Wxzt.servlet.Common.getData_worker;
import Wxzt.servlet.Config.configSql;
import Wxzt.servlet.bean.QueryJsonData;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
* Created by Pengxi on 2015/8/20.
*/
public class Loading extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        getData_worker worker = new getData_worker();
        ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
        try{
            listData = worker.load(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        int count = listData.size();
        if(count==1){
            configSql con = new configSql();
            jsonData.setDataList(listData);
            jsonData.setConfig(con.configOneQuery());/*查询配置参数*/
            jsonData.setDataType("Y");
        }else {
            jsonData.setDataType("N");//无数据
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}
