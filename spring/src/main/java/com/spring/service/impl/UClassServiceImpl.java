package com.spring.service.impl;

import com.spring.entity.UClass;
import com.spring.mapper.UClassMapper;
import com.spring.service.UClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UClassServiceImpl implements UClassService {
    @Resource
    private UClassMapper uClassMapper;
    public List<UClass> getLikeName(String name){
        return uClassMapper.getLikeName(name);
    }

    public List<UClass> getClassList(){
        return uClassMapper.getClassList();
    }

    public int save(UClass c){
        return uClassMapper.save(c);
    }

    public int update(UClass c){
        return uClassMapper.update(c);
    }

    public int delete(String name){
        return uClassMapper.delete(name);
    }

}
