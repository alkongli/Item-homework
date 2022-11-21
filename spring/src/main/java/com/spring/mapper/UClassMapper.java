package com.spring.mapper;

import com.spring.entity.UClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UClassMapper {

    public List<UClass> getClassList();
    public List<UClass> getLikeName(String name);

    public int save(UClass c);

    public int update(UClass c);

    public int delete(String name);


}
