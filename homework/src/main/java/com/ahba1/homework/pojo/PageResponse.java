package com.ahba1.homework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse implements Serializable {
    List<NormalUser> res;
    Integer total;
}
