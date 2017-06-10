package com.wxzt.service;

import com.wxzt.pojo.Dddcdr;
import com.wxzt.pojo.WorkerDayReport;
import com.wxzt.pojo.WorkerDayReportExample;

import java.util.List;

/**
 * Created by yin on 2017/5/25.
 */
public interface ReportService {
    public List<WorkerDayReport> getDayWorkerReport(String date);

    public boolean addWorkerDayReportBatch(List<WorkerDayReport> list);

    public boolean deleteBatch(String date);

    public List<Dddcdr> selectAllCdr(int page,int size);

}
