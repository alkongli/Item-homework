package com.spring.controller;

import com.spring.common.R;
import com.spring.dto.SWorkDto;
import com.spring.entity.SWork;
import com.spring.service.SWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/sWork")
@RestController
@Api(tags="学生提交的相关接口")
public class SWorkController {

    @Resource
    private SWorkService sWorkService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查看学生提交作业情况
     * @param workId
     * @return
     */
    @GetMapping("/list")
    public R<List<SWorkDto>> list(Long workId){
        List<SWorkDto> sWorkList = sWorkService.getSWorkList(workId);

        return R.success(sWorkList);
    }

    /**
     * 查询未提交作业学生学号
     * @param classId
     * @param workId
     * @return
     */
    @ApiOperation(value = "查询未提交作业学生学号")
    @GetMapping("/unFinish")
    public R<List<SWork>> unFinish(Long classId,Long workId){

        List<SWork> sWorkList= sWorkService.getUnFinish(classId, workId);

        return R.success(sWorkList);
    }

    @PostMapping("/add")
    public R<String> save(@RequestBody SWorkDto sWorkDto){

        sWorkDto.setCreateTime(LocalDateTime.now());

       //        截取原始文件名的后缀
        String substring = sWorkDto.getName().substring(sWorkDto.getName().lastIndexOf("."));
//        生成文件保存路径
        String path=sWorkDto.getWorkId()+"_"+sWorkDto.getUserName()+substring;
        sWorkDto.setPath(path);
        sWorkService.save(sWorkDto);

//        创建key，用redis记录提交人数
        String key="work_"+sWorkDto.getWorkId();
        stringRedisTemplate.opsForValue().increment(key);

        System.out.println(sWorkDto);
        return R.success("作业保存成功");
    }


    @DeleteMapping
    public R<String> delete(Long workId,Long sWorkId){
        //    redis记录的提交人数减一
        String key="work_"+workId;
        stringRedisTemplate.opsForValue().decrement(key);

        sWorkService.delete(sWorkId);
        return R.success("作业已退回");
    }

}
