package com.zheng.blog.web;

import com.zheng.blog.service.BlogService;
import com.zheng.blog.service.TagService;
import com.zheng.blog.service.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Api(tags = "前端首页接口")
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @ApiOperation("首页")
    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlogPublished(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(3));
        return "index";
    }
    @ApiOperation("搜索博客")
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%" +query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }
    @ApiOperation("根据id 查询博客")
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) throws NotFoundException {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }
    @ApiOperation("前端底部最新博客接口")
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

}
