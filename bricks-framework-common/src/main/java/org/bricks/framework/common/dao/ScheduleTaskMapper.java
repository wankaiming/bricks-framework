package org.bricks.framework.common.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.bricks.framework.common.entity.ScheduleTask;
import org.bricks.framework.common.entity.ScheduleTaskExample;

public interface ScheduleTaskMapper {
    long countByExample(ScheduleTaskExample example);

    int deleteByExample(ScheduleTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleTask record);

    int insertSelective(ScheduleTask record);

    List<ScheduleTask> selectByExample(ScheduleTaskExample example);

    ScheduleTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScheduleTask record, @Param("example") ScheduleTaskExample example);

    int updateByExample(@Param("record") ScheduleTask record, @Param("example") ScheduleTaskExample example);

    int updateByPrimaryKeySelective(ScheduleTask record);

    int updateByPrimaryKey(ScheduleTask record);
}