package com.spring.mapper;

import com.spring.dto.WorkDto;
import com.spring.entity.Work;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkMapper {

    public List<Work> getWorkList();

    public List<Work> getWorkListByClass(Long classId,Long userId);

    public List<Work> getWorkListByClassF(Long classId,Long userId);

    public List<WorkDto> getWorkListByCreate(Long createId);

    public List<Work> getByName(String name);

    public int save(Work work);

    public int update(Work work);

    public int delete(Long id);


}
