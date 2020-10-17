package com.ahba1.homework.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Setter
@Getter
public class UploadParam implements Serializable {

    private String username;
    private MultipartFile pic;

}
