package com.spring.service.impl;

import com.spring.dto.SWorkDto;
import com.spring.entity.SWork;
import com.spring.mapper.SWorkMapper;
import com.spring.service.SWorkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SWorkServiceImpl implements SWorkService {
    @Resource
    private SWorkMapper sWorkMapper;

    public List<SWorkDto> getSWorkList(Long workId){
        return sWorkMapper.getSWorkList(workId);
    }

    public List<SWork> getUnFinish(Long classId,Long workId){
        return sWorkMapper.getUnFinish(classId,workId);
    }

    public int save(SWork sWork){
        return sWorkMapper.save(sWork);
    }

    public int update(SWork sWork){
        return sWorkMapper.update(sWork);
    }

    public int delete(Long id){
        return sWorkMapper.delete(id);
    }

}
