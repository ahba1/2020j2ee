package com.ahba1.homework.mapper;

import com.ahba1.homework.pojo.NormalUser;
import com.ahba1.homework.pojo.NormalUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NormalUserMapper {
    int countByExample(NormalUserExample example);

    int deleteByExample(NormalUserExample example);

    int deleteByPrimaryKey(String username);

    int insert(NormalUser record);

    int insertSelective(NormalUser record);

    List<NormalUser> selectByExampleWithBLOBs(NormalUserExample example);

    List<NormalUser> selectByExample(NormalUserExample example);

    NormalUser selectByPrimaryKey(String username);

    int updateByExampleSelective(@Param("record") NormalUser record, @Param("example") NormalUserExample example);

    int updateByExampleWithBLOBs(@Param("record") NormalUser record, @Param("example") NormalUserExample example);

    int updateByExample(@Param("record") NormalUser record, @Param("example") NormalUserExample example);

    int updateByPrimaryKeySelective(NormalUser record);

    int updateByPrimaryKeyWithBLOBs(NormalUser record);

    int updateByPrimaryKey(NormalUser record);
}