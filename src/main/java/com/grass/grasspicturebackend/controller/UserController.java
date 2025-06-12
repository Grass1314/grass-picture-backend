package com.grass.grasspicturebackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grass.grasspicturebackend.annotation.AuthCheck;
import com.grass.grasspicturebackend.common.BaseResponse;
import com.grass.grasspicturebackend.common.DeleteRequest;
import com.grass.grasspicturebackend.common.ResultUtils;
import com.grass.grasspicturebackend.constant.UserConstant;
import com.grass.grasspicturebackend.exception.BusinessException;
import com.grass.grasspicturebackend.exception.ErrorCode;
import com.grass.grasspicturebackend.exception.ThrowUtils;
import com.grass.grasspicturebackend.model.dto.user.*;
import com.grass.grasspicturebackend.model.entity.User;
import com.grass.grasspicturebackend.model.vo.LoginUserVo;
import com.grass.grasspicturebackend.model.vo.UserVo;
import com.grass.grasspicturebackend.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Mr.Liuxq
 * @version 1.0
 * @description: 用户控制器
 * @date 2025/6/11 16:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * @param userRegisterRequest 用户注册信息
     * @return com.grass.grasspicturebackend.common.BaseResponse<java.lang.Long>
     * @description: 用户 注册
     * @author: Mr.Liuxq
     * @date 2025/6/11 16:34
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@Validated @RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        Long id = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(id);
    }

    /**
     * @param userLoginRequest 登录请求参数
     * @param request          HTTP请求
     * @return com.grass.grasspicturebackend.common.BaseResponse<com.grass.grasspicturebackend.model.vo.LoginUserVo>
     * @description: 用户登录
     * @author: Mr.Liuxq
     * @date 2025/6/12 10:37
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVo> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        LoginUserVo loginUserVo = userService.userLogin(userLoginRequest, request);
        return ResultUtils.success(loginUserVo);
    }

    /**
     * @param request HTTP请求
     * @return com.grass.grasspicturebackend.common.BaseResponse<com.grass.grasspicturebackend.model.vo.LoginUserVo>
     * @description: 获取当前登录用户
     * @author: Mr.Liuxq
     * @date 2025/6/12 10:37
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVo> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVo(loginUser));
    }

    /**
     * @param request HTTP请求
     * @return com.grass.grasspicturebackend.common.BaseResponse<java.lang.Boolean>
     * @description: 用户登出
     * @author: Mr.Liuxq
     * @date 2025/6/12 10:37
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    // <editor-fold desc="管理员操作">

    /**
     * @description: 新增用户
     * @author: Mr.Liuxq
     * @date 2025/6/12 14:42
     * @param userAddRequest 新增用户信息
     * @return com.grass.grasspicturebackend.common.BaseResponse<java.lang.Long> 新增用户ID
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        ThrowUtils.throwIf(userAddRequest == null, ErrorCode.PARAMS_ERROR);
        User user = new User();
        BeanUtil.copyProperties(userAddRequest, user);
        // 密码
        if (StrUtil.isBlank(user.getUserPassword())) {
            final String DEFAULT_PASSWORD = "12345678";
            // 加密
            String encryptPassword = userService.getEncryptPassword(DEFAULT_PASSWORD);
            user.setUserPassword(encryptPassword);
        } else {
            // 密码加密
            user.setUserPassword(userService.getEncryptPassword(user.getUserPassword()));
        }
        // 角色
        if (StrUtil.isBlank(user.getUserRole())) {
            user.setUserRole(UserConstant.DEFAULT_ROLE);
        }
        // 新增
        boolean save = userService.save(user);
        ThrowUtils.throwIf(!save, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * @description: 获取用户 (管理员使用)
     * @author: Mr.Liuxq
     * @date 2025/6/12 14:42
     * @param id 用户ID
     * @return com.grass.grasspicturebackend.common.BaseResponse<com.grass.grasspicturebackend.model.entity.User>
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(Long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * @description: 根据用户ID获取用户信息
     * @author: Mr.Liuxq
     * @date 2025/6/12 14:49
     * @param id 用户ID
     * @return com.grass.grasspicturebackend.common.BaseResponse<com.grass.grasspicturebackend.model.vo.UserVo> 用户信息
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVo> getUserVoById(Long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        UserVo userVo = userService.getUserVo(user);
        return ResultUtils.success(userVo);
    }

    /**
     * @description: 删除用户
     * @author: Mr.Liuxq
     * @date 2025/6/12 14:49
     * @param deleteRequest 删除用户信息
     * @return com.grass.grasspicturebackend.common.BaseResponse<java.lang.Boolean>
     */
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * @description: 更新用户
     * @author: Mr.Liuxq
     * @date 2025/6/12 14:59
     * @param userUpdateRequest 更新用户信息
     * @return com.grass.grasspicturebackend.common.BaseResponse<java.lang.Boolean>
     */
    @PutMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * @description: 获取用户列表 (仅管理员)
     * @author: Mr.Liuxq
     * @date 2025/6/12 14:59
     * @param userQueryRequest 查询用户信息
     * @return com.grass.grasspicturebackend.common.BaseResponse<com.baomidou.mybatisplus.core.metadata.IPage<com.grass.grasspicturebackend.model.vo.UserVo>>
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVo>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Long current = userQueryRequest.getCurrent();
        Long pageSize = userQueryRequest.getPageSize();
        // 分页查询
        Page<User> userPage = userService.page(new Page<>(current, pageSize), userService.getQueryWrapper(userQueryRequest));
        // 构造返回
        Page<UserVo> userVoPage = new Page<>(current, pageSize, userPage.getTotal());
        // 列表装换
        List<UserVo> userVoList = userService.getUserVoList(userPage.getRecords());
        userVoPage.setRecords(userVoList);
        return ResultUtils.success(userVoPage);
    }

    // </editor-fold>
}
