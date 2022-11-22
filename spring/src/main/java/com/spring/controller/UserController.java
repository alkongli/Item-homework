package com.spring.controller;

import com.spring.common.BaseContext;
import com.spring.common.R;
import com.spring.entity.User;
import com.spring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


import java.util.List;


@RequestMapping("/user")
@RestController
@Api(tags="用户的相关接口")
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
    public R<User> login(HttpServletRequest request, @RequestBody User user){
//        根据name查找账号
        User u=userService.getByName(user.getName());

        if(u==null)
            return R.error("账号错误");
        if(!u.getPassword().equals(user.getPassword()))
            return R.error("密码错误");
        if(u.getStatus()==1)
            return R.error("账号已被封");

//        登录成功
        request.getSession().setAttribute("user",u.getId());
        return R.success(u);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return R.success("已退出登录");
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
    @ApiOperation(value = "管理员修改用户信息")
    @PutMapping
    public R<String> update(@RequestBody User user){
        userService.update(user);
        return R.success("修改成功");
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @ApiOperation(value = "用户修改密码")
    @PutMapping("/password")
    public R<String> updatePassword(@RequestBody User user){
        user.setId(BaseContext.getCurrentId());
        userService.updatePassword(user);
        return R.success("密码修改成功");
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
