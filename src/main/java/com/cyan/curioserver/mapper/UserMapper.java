package com.cyan.curioserver.mapper;

import com.cyan.curioserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select id, username, created_at AS createAt from curio.user where id = #{id}")
    User findById(Long id);
}
