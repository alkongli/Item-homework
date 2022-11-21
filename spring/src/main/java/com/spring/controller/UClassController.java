package com.spring.controller;

import com.spring.common.R;
import com.spring.entity.UClass;

import com.spring.service.UClassService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/uClass")
@RestController
public class UClassController {
    @Resource
    private UClassService UClassService;

    @GetMapping("/list")
    public R<List<UClass>> list(){
        List<UClass> classList = UClassService.getClassList();

        return R.success(classList);
    }

    @GetMapping("/like")
    public R<List<UClass>> getLikeName(String text) {
        text = "%" + text + "%";
        List<UClass> list = UClassService.getLikeName(text);

        return R.success(list);
    }

    @PostMapping("/add")
    public R<String> save(@RequestBody UClass c){
        UClassService.save(c);

        return R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody UClass c){

        UClassService.update(c);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(String name){
        UClassService.delete(name);

        return R.success("删除成功");
    }

}
