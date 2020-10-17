package com.ahba1.homework.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ActivateParam implements Serializable {

    private String username;
    private String password;
    private MultipartFile pic;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getPic() {
        return pic;
    }

    public void setPic(MultipartFile pic) {
        this.pic = pic;
    }
}
