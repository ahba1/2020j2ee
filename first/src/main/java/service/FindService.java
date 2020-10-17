package service;

import pojo.NormalUser;

import java.util.List;

public interface FindService {

    List<NormalUser> find(String path, String key , String value);

}
