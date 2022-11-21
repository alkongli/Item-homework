package com.spring.service;
import com.spring.entity.User;
import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getUserList();

    public User getByName(String name);
    public List<User> getLikeName(String name);

    public int save(User user);

    public int update(User user);

    public int delete(Long id);
}
