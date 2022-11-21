package com.spring.mapper;
import com.spring.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    public List<User> getUserList();

    public User getByName(String name);

    public List<User> getLikeName(String name);

    public int save(User user);

    public int update(User user);

    public int delete(Long id);
}
