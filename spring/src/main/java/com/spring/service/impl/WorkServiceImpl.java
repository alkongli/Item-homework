package com.spring.service.impl;



import com.spring.dto.WorkDto;
import com.spring.entity.Work;
import com.spring.mapper.WorkMapper;
import com.spring.service.WorkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class WorkServiceImpl implements WorkService {
    @Resource
    private WorkMapper workMapper;

    public List<Work> getWorkList(){
        return workMapper.getWorkList();
    }

    /**
     * 连接查询，查询未完成作业
     * @param classId
     * @param userId
     * @return
     */
    public List<Work> getWorkListByClass(Long classId,Long userId){
        return workMapper.getWorkListByClass(classId,userId);
    }

    /**
     * 连接查询，查询完成作业
     * @param classId
     * @param userId
     * @return
     */
    public List<Work> getWorkListByClassF(Long classId,Long userId){
        return workMapper.getWorkListByClassF(classId,userId);
    }

    public List<WorkDto> getWorkListByCreate(Long createId){
        return workMapper.getWorkListByCreate(createId);
    }

    public List<Work> getByName(String name){
        return workMapper.getByName(name);
    }

    public int save(Work work){
        return workMapper.save(work);
    }

    public int update(Work work){
        return workMapper.update(work);
    }

    public int delete(Long id){
        return workMapper.delete(id);
    }

}
