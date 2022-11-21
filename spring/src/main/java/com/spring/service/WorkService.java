package com.spring.service;

import com.spring.dto.WorkDto;
import com.spring.entity.User;
import com.spring.entity.Work;

import java.util.List;
import java.util.Map;

public interface WorkService {

    public List<Work> getWorkList();

    public List<Work> getWorkListByClass(Long classId,Long userId);

    public List<Work> getWorkListByClassF(Long classId,Long userId);

    public List<WorkDto> getWorkListByCreate(Long createId);

    public List<Work> getByName(String name);

    public int save(Work work);

    public int update(Work work);

    public int delete(Long id);

}
