package com.spring.controller;

import com.spring.common.R;
import com.spring.dto.WorkDto;
import com.spring.entity.Work;
import com.spring.service.WorkService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/work")
@RestController
public class WorkController {
    @Resource
    private WorkService workService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 学生查看班级作业
     * @param classId
     * @param userId
     * @return
     */
    @GetMapping("/sList")
    public R<List<Work>> sList(Long classId, Long userId){

//         查询当前班级下,学生未完成的作业
        List<Work> work = workService.getWorkListByClass(classId, userId);

        return R.success(work);
    }

    /**
     * 教师查看发布的作业情况
     * @param createId
     * @return
     */
    @GetMapping("/tList")
    public R<List<WorkDto>> tList(Long createId){

//            查询当前教师发布的作业
        List<WorkDto> workDto = workService.getWorkListByCreate(createId);

//        从redis获取提交作业总人数
        List<WorkDto> list=workDto.stream().map((item)->{
            String s = stringRedisTemplate.opsForValue().get("work_"+item.getId());
            if(s!=null)
                item.setNum(Integer.valueOf(s));
            else
                item.setNum(0);
            return item;
        }).collect(Collectors.toList());

        return R.success(list);
    }

    /**
     * 学生已完成作业
     * @param classId
     * @param userId
     * @return
     */
    @GetMapping("finish")
    public R<List<Work>> finish(Long classId, Long userId){
//            查询当前班级下,学生完成的作业
        List<Work> list= workService.getWorkListByClassF(classId, userId);

        return R.success(list);
    }

    @GetMapping("/like")
    public R<List<Work>> getByName(String name){
        name="%"+name+"%";
        List<Work> list = workService.getByName(name);
        return R.success(list);
    }


    /**
     * 发布作业
     * @param workDto
     * @return
     */
    @PostMapping("/add")
    public R<String> save(@RequestBody WorkDto workDto){
        LocalDateTime localDateTime=LocalDateTime.now();
        workDto.setCreateTime(localDateTime);
//        设置作业截止时间
        workDto.setEndTime(localDateTime.plusDays(workDto.getDays()));
        workService.save(workDto);

        return R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody WorkDto workDto){

//        延长作业时间
        if(workDto.getDays()!=0){
            LocalDateTime localDateTime=LocalDateTime.now();
            workDto.setEndTime(localDateTime.plusDays(workDto.getDays()));
        }
        workService.update(workDto);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Work work){
        workService.delete(work.getId());
//        移除相关key
        String key="work_"+work.getId();
        stringRedisTemplate.delete(key);

        return R.success("删除成功");
    }
}
