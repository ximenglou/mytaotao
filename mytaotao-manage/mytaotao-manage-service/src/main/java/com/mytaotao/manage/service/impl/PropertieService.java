package com.mytaotao.manage.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by liqiyu on 2016/08/12 1:59.
 */
@Service
public class PropertieService {
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
