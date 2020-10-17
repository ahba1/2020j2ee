package com.ahba1.homework.service;

import com.ahba1.homework.pojo.ActivateParam;
import com.ahba1.homework.pojo.NormalUser;
import com.ahba1.homework.pojo.PageResponse;
import com.ahba1.homework.pojo.UploadParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NormalUserService {

    NormalUser activate(ActivateParam param);

    NormalUser login(String username, String password);

    List<NormalUser> find(MultipartFile pic);

    void uploadPic(UploadParam param);

    PageResponse get(String key, String value, Integer page, Integer size);

}
