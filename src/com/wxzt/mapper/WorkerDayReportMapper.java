package com.wxzt.mapper;

import com.wxzt.pojo.WorkerDayReport;
import com.wxzt.pojo.WorkerDayReportExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WorkerDayReportMapper {
    int countByExample(WorkerDayReportExample example);

    int deleteByExample(WorkerDayReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkerDayReport record);

    int insertSelective(WorkerDayReport record);

    List<WorkerDayReport> selectByExample(WorkerDayReportExample example);

    List<WorkerDayReport> getWorkerDayForReport(Map map);

    WorkerDayReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WorkerDayReport record, @Param("example") WorkerDayReportExample example);

    int updateByExample(@Param("record") WorkerDayReport record, @Param("example") WorkerDayReportExample example);

    int updateByPrimaryKeySelective(WorkerDayReport record);

    int updateByPrimaryKey(WorkerDayReport record);

    int insertBatch(List<WorkerDayReport> list);
}