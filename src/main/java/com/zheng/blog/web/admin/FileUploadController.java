package com.zheng.blog.web.admin;

import com.zheng.blog.util.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/admin")
@Api(tags = "后台文件上传接口")
public class FileUploadController {

    @ApiOperation("文件首页显示")
    @RequestMapping("/file")
    public String file(){
        return "admin/file";
    }

    @ApiOperation("文件上传接口")
    @RequestMapping(value = "fileUpload",method = POST)
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if(file.isEmpty()){
            return "admin/file-error";
        }
        String fileName = file.getOriginalFilename();
        int size =(int) file.getSize();
        System.out.println(fileName + "-->" + size);
            String path = "/root/fileUpload";
        //String path = "E:\\fileUpLoad";
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            attributes.addAttribute("message","文件上传成功");
            return "redirect:/admin/file";
        } catch (IOException e) {
            e.printStackTrace();
            return "admin/file-error";
        }
    }

    @ApiOperation("文件夹首页显示")
    @RequestMapping("/files")
    public String multiFile(){
        return "admin/files";
    }

    @ApiOperation("文件夹上传接口")
    @RequestMapping(value = "/filesUpload",method=POST)
    public String multiFileUpload(MultipartFile[] files, RedirectAttributes attributes) {
        if(files.length == 1 && files[0].isEmpty()){
            return "admin/file-error";
        }
        UploadFileUtil.saveMultiFile("E:\\fileUpLoad", files);
        attributes.addAttribute("message","文件上传成功");
        return "redirect:/admin/file";
    }
}
