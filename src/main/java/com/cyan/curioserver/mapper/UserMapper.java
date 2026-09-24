package com.cyan.curioserver.mapper;

import com.cyan.curioserver.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select id, username, created_at AS createAt from curio.user where id = #{id}")
    User findById(Long id);

    @Select("SELECT id, username, password_hash AS passwordHash ,created_at AS createAt " +
            "from curio.user where username = #{username}")
    User findByUsername(@Param("username")String username);

    @Insert("INSERT INTO curio.user (username, password_hash) VALUES (#{username},#{passwordHash})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(User user);
}
