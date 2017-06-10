package com.wxzt.web.controller;

import com.wxzt.pojo.Dddcdr;
import com.wxzt.pojo.WorkerDayReport;
import com.wxzt.pojo.WorkerDayReportExample;
import com.wxzt.service.ReportService;
import com.wxzt.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yin on 2017/5/25.
 */
@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    public void ProduceReportForWorkerDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        //生成的数据
        List<WorkerDayReport> list = this.reportService.getDayWorkerReport(date);
        //先批量删除当天的数据
         this.reportService.deleteBatch(date);
        //批量添加最新生成的数据
        this.reportService.addWorkerDayReportBatch(list);
    }

   @RequestMapping("getAllCdr")
   @ResponseBody
    public List<Dddcdr> getAllCdr(int page,int size){
       List<Dddcdr> list = this.reportService.selectAllCdr(page,size);
       return list;
    }


}
