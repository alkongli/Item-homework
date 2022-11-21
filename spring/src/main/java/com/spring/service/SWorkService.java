package com.spring.service;

import com.spring.dto.SWorkDto;
import com.spring.entity.SWork;

import java.util.List;

public interface SWorkService {

    public List<SWorkDto> getSWorkList(Long workId);

    public List<SWork> getUnFinish(Long classId,Long workId);

    public int save(SWork sWork);

    public int update(SWork sWork);

    public int delete(Long id);

}
