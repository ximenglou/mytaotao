package com.mytaotao.manage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaotao.common.bean.PicUploadResult;
import com.mytaotao.manage.service.impl.PropertieService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by liqiyu on 2016/08/11 22:51.
 */
@RequestMapping("pic")
@Controller
public class PicUploadController {
    @Autowired
    PropertieService propertieService;

    private static final Logger LOG = LoggerFactory.getLogger(PicUploadController.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile) throws JsonProcessingException {
        if (LOG.isInfoEnabled()) {
            LOG.info("------李奇玉------uploadFile值=" + uploadFile + "," + "当前类=PicUploadController.upload()---开始处理上传");
        }
        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setError(1);
        if (uploadFile.isEmpty() || uploadFile == null) {
            if (LOG.isInfoEnabled()) {
                LOG.info("------李奇玉------uploadFile值=" + uploadFile + "," + "当前类=PicUploadController.upload()---未获取到上传的文件");
            }
            return MAPPER.writeValueAsString(picUploadResult);
        }
        String[] strings = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
        String originalFilename = uploadFile.getOriginalFilename();
        boolean flag = true;
        for (String string : strings) {
            if (originalFilename.endsWith(string)) {
                if(LOG.isInfoEnabled()){
                LOG.info("------李奇玉------处理后缀名为" + string + "的图片," + "当前类=PicUploadController.upload()");
                }
                flag = false;
            }
        }
        if (flag) {
            if(LOG.isInfoEnabled()){
            LOG.info("------李奇玉------图片originalFilename的后缀名不合格" + "," + "当前类=PicUploadController.upload()");
            }
            return MAPPER.writeValueAsString(picUploadResult);
        }
        //初步验证通过
        //获取路径
        String path = getPath(originalFilename);
        File file = new File(propertieService.REPOSITORY_PATH + path);


        try {
            uploadFile.transferTo(file);
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                picUploadResult.setHeight(image.getHeight() + "");
                picUploadResult.setWidth(image.getWidth() + "");
            }

        } catch (IOException e) {
            LOG.error("------李奇玉------检测到" + originalFilename + "不是一个有效的图片,拒绝上传," + "当前类=PicUploadController.upload()");

            e.printStackTrace();
            if (file.exists()) {
                file.delete();
            }
            return MAPPER.writeValueAsString(picUploadResult);
        }

        picUploadResult.setError(0);
        picUploadResult.setUrl(propertieService.IMAGE_BASE_URL + path);
        if(LOG.isInfoEnabled()){
        LOG.info("------李奇玉------上传成功,图片路径为" + propertieService.IMAGE_BASE_URL + path + "," + "当前类=PicUploadController.upload()");
        }
        return MAPPER.writeValueAsString(picUploadResult);
    }

    private String getPath(String originalFilename) {
        Date date = new Date();
        DateTime dateTime = new DateTime(date);
        String path = "/" + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/" + dateTime.toString("dd");
        File filepath = new File(propertieService.REPOSITORY_PATH+path);
        if(!filepath.isDirectory()){
            filepath.mkdirs();
        }
        return path + "/" + UUID.randomUUID().toString().replace("-", "") + originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
