package com.ahba1.homework.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class ImageUtil {
    public static String multipartFileToBASE64(MultipartFile mFile) {
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            return encoder.encode(mFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
