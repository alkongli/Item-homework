package com.spring.mapper;

import com.spring.dto.SWorkDto;
import com.spring.entity.SWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SWorkMapper {

    public List<SWorkDto> getSWorkList(Long workId);

    public List<SWork> getUnFinish(Long classId,Long workId);

    public int save(SWork sWork);

    public int update(SWork sWork);

    public int delete(Long id);
}
