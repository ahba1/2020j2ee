package com.ahba1.homework.controller;

import com.ahba1.homework.pojo.ActivateParam;
import com.ahba1.homework.pojo.NormalUser;
import com.ahba1.homework.pojo.PageResponse;
import com.ahba1.homework.pojo.UploadParam;
import com.ahba1.homework.service.NormalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController("/")
public class NormalUserController {

    @Autowired
    NormalUserService normalUserService;

    @PostMapping("/activate")
    public NormalUser activate(ActivateParam param){
        return normalUserService.activate(param);
    }

    @PostMapping("/login")
    public NormalUser login(String username, String password, HttpServletRequest req){
        NormalUser normalUser = normalUserService.login(username, password);
        req.getSession().setMaxInactiveInterval(2*60*60);
        req.getSession().setAttribute("user", normalUser);
        return normalUserService.login(username, password);
    }

    @PostMapping("/upload")
    public void uploadPic(UploadParam param){
        normalUserService.uploadPic(param);
    }

    @GetMapping("/find")
    public List<NormalUser> find(MultipartFile pic){
        return normalUserService.find(pic);
    }

    @GetMapping("/get")
    public PageResponse get(@PathParam("key") String key, @PathParam("value") String value,
                            @PathParam("page") Integer page, @PathParam("size") Integer size){
        return normalUserService.get(key, value, page, size);
    }
}
