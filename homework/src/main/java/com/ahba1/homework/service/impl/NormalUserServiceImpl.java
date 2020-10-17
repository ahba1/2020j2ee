package com.ahba1.homework.service.impl;

import com.ahba1.homework.HomeworkApplication;
import com.ahba1.homework.configuration.FaceApiConfiguration;
import com.ahba1.homework.mapper.NormalUserMapper;
import com.ahba1.homework.pojo.*;
import com.ahba1.homework.service.NormalUserService;
import com.ahba1.homework.utils.ImageUtil;
import com.baidu.aip.face.AipFace;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class NormalUserServiceImpl implements NormalUserService {

    @Autowired
    FaceApiConfiguration apiConfiguration;

    @Autowired
    NormalUserMapper normalUserMapper;

    @Autowired
    RestTemplate restTemplate;

    private Map<Pair<String, String>, List<NormalUser>> cache = new HashMap<>();

    @Transactional
    @Override
    public NormalUser activate(ActivateParam param) {
        //验证身份
        NormalUser normalUser = normalUserMapper.selectByPrimaryKey(param.getUsername());
        if (normalUser.getPassword().equals(param.getPassword())){
            //插入图片
            try {
                normalUser.setPic(param.getPic().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            normalUserMapper.updateByPrimaryKeyWithBLOBs(normalUser);
            //转换为base64
            String base64 = ImageUtil.multipartFileToBASE64(param.getPic());
            if ("".equals(base64)){
                return null;
            }
            AipFace client = new AipFace(apiConfiguration.getAppId(), apiConfiguration.getApiKey(), apiConfiguration.getSecretKey());
            JSONObject res = client.addUser(base64, apiConfiguration.getImageType(), apiConfiguration.getGroupId(), normalUser.getUsername(), null);
            if (res.get(apiConfiguration.getTag()).equals(apiConfiguration.getSuccess())){
                return normalUser;
            }
        }

        return null;
    }

    @Override
    public NormalUser login(String username, String password) {
        NormalUser normalUser = normalUserMapper.selectByPrimaryKey(username);
        return (normalUser!=null&& normalUser.getPassword().equals(password))?normalUser:null;
    }

    @Override
    public List<NormalUser> find(MultipartFile pic) {
        //获取图片base64编码
        String base64 = ImageUtil.multipartFileToBASE64(pic);
        //访问api获取personId
        AipFace client = new AipFace(apiConfiguration.getAppId(), apiConfiguration.getApiKey(), apiConfiguration.getSecretKey());
        JSONObject res = client.search(base64, apiConfiguration.getImageType(), apiConfiguration.getGroupId(), null);
        HomeworkApplication.logger.info(res.toString());
        System.out.println(res);
        //使用personId获取User实体
        if (res.get(apiConfiguration.getTag()).equals(apiConfiguration.getSuccess())){
            JSONArray persons = res.getJSONObject("result")
                    .getJSONArray("user_list");
            List<NormalUser> users = new LinkedList<>();
            for (int i = 0; i < persons.length(); i++){
                if (persons.getJSONObject(i).getDouble("score")>50.0F){
                    users.add(normalUserMapper.selectByPrimaryKey(persons.getJSONObject(i).getString("user_id")));
                }
            }

            return users;
        }
        return null;
    }

    @Transactional
    @Override
    public void uploadPic(UploadParam param) {
        NormalUser normalUser = normalUserMapper.selectByPrimaryKey(param.getUsername());
        //插入图片
        try {
            normalUser.setPic(param.getPic().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        normalUserMapper.updateByPrimaryKeyWithBLOBs(normalUser);
        //转换为base64
        String base64 = ImageUtil.multipartFileToBASE64(param.getPic());

        AipFace client = new AipFace(apiConfiguration.getAppId(), apiConfiguration.getApiKey(), apiConfiguration.getSecretKey());
        JSONObject res = client.updateUser(base64, apiConfiguration.getImageType(), apiConfiguration.getGroupId(), normalUser.getUsername(), null);
        HomeworkApplication.logger.info(res.toString());
    }

    @Override
    public PageResponse get(String key, String value, Integer page, Integer size) {
        Pair<String, String> pair = new Pair<>(key, value);
        if (cache.containsKey(pair)){
            return doPage(cache.get(pair), page, size);
        }
        NormalUserExample example = new NormalUserExample();
        NormalUserExample.Criteria criteria = example.createCriteria();
        value = "%"+value+"%";
        switch (key){
            case "username":
                criteria.andUsernameLike(value);
                break;
            case "name":
                criteria.andNameLike(value);
                break;
            case "QQ":
                criteria.andQqLike(value);
                break;
            case "phone":
                criteria.andPhoneNumLike(value);
                break;
            case "email":
                criteria.andEmailLike(value);
                break;
        }
        List<NormalUser> res = normalUserMapper.selectByExample(example);
        cache.put(pair, res);
        return doPage(res, page, size);
    }

    private PageResponse doPage(List<NormalUser> users, Integer page, Integer size) {
        if (page==null||size==null){
            return new PageResponse(users, users.size());
        }
        if (page*size<users.size()&&(page*size+size)<users.size()){
            return new PageResponse(users.subList(page*size, page*size+size), users.size());
        }else if (page*size<users.size()&&(page*size+size)>=users.size()){
            return new PageResponse(users.subList(page*size, users.size()), users.size());
        }
        return null;
    }
}
