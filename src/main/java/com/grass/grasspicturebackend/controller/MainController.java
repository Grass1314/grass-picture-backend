package com.grass.grasspicturebackend.controller;

import com.grass.grasspicturebackend.common.BaseResponse;
import com.grass.grasspicturebackend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author Mr.Liuxq
 * @date 2025/6/11 09:48
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping("/health")
    public BaseResponse<String> health() {
        return ResultUtils.success("ok");
    }
}
