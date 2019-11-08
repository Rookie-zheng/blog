package com.zheng.blog.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(tags = "前端关于我接口")
public class AboutShowController {
    @ApiOperation("关于我接口")
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
