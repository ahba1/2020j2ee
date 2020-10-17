package pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class NormalUser implements Serializable {
    @ExcelProperty("学号")
    String username;
    @ExcelProperty("姓名")
    String name;
    @ExcelProperty("电话号码")
    String phoneNum;
    @ExcelProperty("QQ")
    String qq;
    @ExcelProperty("邮箱")
    String email;
}
