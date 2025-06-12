package com.grass.grasspicturebackend.mapper;

import com.grass.grasspicturebackend.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Mr.Liuxq
 * @description 针对表【user(用户)】的数据库操作Mapper
 * @createDate 2025-06-11 14:45:28
 * @Entity generator.domain.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




