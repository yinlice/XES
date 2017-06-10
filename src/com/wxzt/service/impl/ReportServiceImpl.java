package com.wxzt.service.impl;

import Wxzt.tool.JDBC;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxzt.mapper.DddcdrMapper;
import com.wxzt.mapper.WorkerDayReportMapper;
import com.wxzt.pojo.Dddcdr;
import com.wxzt.pojo.DddcdrExample;
import com.wxzt.pojo.WorkerDayReport;
import com.wxzt.pojo.WorkerDayReportExample;
import com.wxzt.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yin on 2017/5/25.
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private WorkerDayReportMapper workerDayReportMapper;
    @Autowired
    private DddcdrMapper dddcdrMapper;
    /**
     * 查询当天的坐席通话量数据
     * @param date
     * @return
     */
    @Override
    public List<WorkerDayReport> getDayWorkerReport(String date) {
        Map map = new HashMap();
        map.put("date",date);
        List<WorkerDayReport> list = this.workerDayReportMapper.getWorkerDayForReport(map);
        return list;
    }

    /**
     * 批量插入的方法
     * @param list
     * @return
     */
    @Override
    public boolean addWorkerDayReportBatch(List<WorkerDayReport> list){
        int [] ints = new int[]{};
        try{
            JDBC jdbc = new JDBC();
            Connection con = jdbc.getCon();
            Statement statement = con.createStatement();
            for (int i = 0; i <list.size(); i++) {
                WorkerDayReport report = list.get(i);
                int total = report.getCall()+report.getNocall();
                String sql = " insert into worker_Day_Report (worknum, dtUpdate,call, nocall, total) values ('"+report.getWorknum()+"','"+
                        report.getDtupdate()+"',"+report.getCall()+","+report.getNocall()+","+total+")";
                statement.addBatch(sql);
            }
            ints = statement.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        }
       if(ints.length>0){
            return true;
       }else{
           return false;
       }
    }

    /**
     * 批量删除
     * @param
     * @return
     */
    @Override
    public boolean deleteBatch(String date) {
        WorkerDayReportExample example = new WorkerDayReportExample();
        WorkerDayReportExample.Criteria criteria = example.createCriteria();
        criteria.andDtupdateEqualTo(date);
        int i = this.workerDayReportMapper.deleteByExample(example);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Dddcdr> selectAllCdr(int page,int size){
        DddcdrExample example = new DddcdrExample();
        PageHelper.startPage(page,size);
        List<Dddcdr> list = this.dddcdrMapper.selectByExample(example);
        PageInfo<Dddcdr> p = new PageInfo<Dddcdr>(list);
        long totalpage = p.getTotal();
        System.out.println("共***   "+totalpage+"条");
        return list;
    }
}
