package com.grass.grasspicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.grass.grasspicturebackend.model.dto.user.UserLoginRequest;
import com.grass.grasspicturebackend.model.dto.user.UserQueryRequest;
import com.grass.grasspicturebackend.model.dto.user.UserRegisterRequest;
import com.grass.grasspicturebackend.model.entity.User;
import com.grass.grasspicturebackend.model.vo.LoginUserVo;
import com.grass.grasspicturebackend.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Mr.Liuxq
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-06-11 14:45:28
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return 注册用户ID
     */
    Long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          请求
     * @return 登录用户信息
     */
    LoginUserVo userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 获取加密密码
     *
     * @param userPassword 密码
     * @return 加密密码
     */
    String getEncryptPassword(String userPassword);

    /**
     * 获取登录用户
     *
     * @param request 请求
     * @return 登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取登录用户信息
     *
     * @param user 用户
     * @return 登录用户信息
     */
    LoginUserVo getLoginUserVo(User user);

    /**
     * 用户登出
     *
     * @param request 请求
     * @return 是否登出成功
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取用户信息
     *
     * @param user 用户
     * @return 用户信息
     */
    UserVo getUserVo(User user);

    /**
     * 获取脱敏后用户列表
     *
     * @param userList 用户列表
     * @return 用户列表
     */
    List<UserVo> getUserVoList(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求
     * @return 查询条件
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
