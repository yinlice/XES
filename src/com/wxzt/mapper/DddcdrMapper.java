package com.wxzt.mapper;

import com.wxzt.pojo.Dddcdr;
import com.wxzt.pojo.DddcdrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DddcdrMapper {
    int countByExample(DddcdrExample example);

    int deleteByExample(DddcdrExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Dddcdr record);

    int insertSelective(Dddcdr record);

    List<Dddcdr> selectByExampleWithBLOBs(DddcdrExample example);

    List<Dddcdr> selectByExample(DddcdrExample example);

    Dddcdr selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Dddcdr record, @Param("example") DddcdrExample example);

    int updateByExampleWithBLOBs(@Param("record") Dddcdr record, @Param("example") DddcdrExample example);

    int updateByExample(@Param("record") Dddcdr record, @Param("example") DddcdrExample example);

    int updateByPrimaryKeySelective(Dddcdr record);

    int updateByPrimaryKeyWithBLOBs(Dddcdr record);

    int updateByPrimaryKey(Dddcdr record);
}