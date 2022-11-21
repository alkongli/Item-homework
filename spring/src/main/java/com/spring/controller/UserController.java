package com.spring.controller;

import com.spring.common.R;
import com.spring.entity.User;
import com.spring.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public R<List<User>> list(){
        List<User> userList = userService.getUserList();
        return R.success(userList);
    }

    /**
     * like 搜索
     * @param text
     * @return
     */
    @GetMapping("/like")
    public R<List<User>> getLikeName(String text){
        text="%"+text+"%";
        List<User> list = userService.getLikeName(text);
        return R.success(list);
    }



    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    @GetMapping()
    public R<User> getByName(String name){
        User u = userService.getByName(name);
        if(u!=null)
            return R.success(u);
        return R.error("未找到该用户");
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody User user){
//        根据name查找账号
        User u=userService.getByName(user.getName());

        if(u==null)
            return R.error("账号错误");
        if(!u.getPassword().equals(user.getPassword()))
            return R.error("密码错误");
        if(u.getStatus()==1)
            return R.error("账号已被封");
        return R.success(u);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/add")
    public R<String> save(@RequestBody User user){
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
        return R.success("添加成功");
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody User user){
        userService.update(user);
        return R.success("修改成功");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        userService.delete(id);
        return R.success("删除成功");
    }


}
