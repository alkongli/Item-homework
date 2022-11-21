package com.spring.service;

import com.spring.entity.UClass;

import java.util.List;

public interface UClassService {
    public List<UClass> getClassList();

    public List<UClass> getLikeName(String name);

    public int save(UClass c);

    public int update(UClass c);

    public int delete(String name);

}
