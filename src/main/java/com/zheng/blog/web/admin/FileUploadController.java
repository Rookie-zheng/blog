package com.zheng.blog.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/admin")
public class FileUploadController {

    @RequestMapping("/file")
    public String file(){
        return "admin/file";
    }


    @RequestMapping(value = "fileUpload",method = POST)
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size =(int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "E:\\fileUpLoad";
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            return "true";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    @RequestMapping("/multifile")
    public String multifile(){
        return "admin/multifile";
    }

    @RequestMapping(value = "/multifileUpload",method=POST)
    public @ResponseBody String  multifileUpload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");

        if(files.isEmpty()){
            return "false";
        }
        String path = "E:\\fileUpLoad";
        for (MultipartFile file : files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return "false";
            } else {
                File dest = new File(path + "/" + fileName);
                if(!dest.getParentFile().exists()){
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }

}
