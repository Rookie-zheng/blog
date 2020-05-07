package com.zheng.blog.web;

import com.zheng.blog.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(tags = "前端归档接口")
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @ApiOperation("文件归档接口")
    @GetMapping("/archives")
    public String archives(@PageableDefault(size = Integer.MAX_VALUE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog(pageable));
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
