package com.grass.grasspicturebackend.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Mr.Liuxq
 * @version 1.0
 * @description: 用户注册请求
 * @date 2025/6/11 14:57
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 7508567074023970845L;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String userAccount;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String userPassword;

    /**
     * 校验密码
     */
    @NotBlank(message = "校验密码不能为空")
    private String checkPassword;
}
