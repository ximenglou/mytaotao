package com.mytaotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiyu on 2016/08/15 11:25.
 */
@Service
public class IndexService {
    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;

    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;

    @Value("${INDEX_AD2_URL}")
    private String INDEX_AD2_URL;

    public static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);

    public String queryAD1() {
        String URL = "http://manage.mytaotao.com" + "/rest/api/content?categoryId=39&page=1&rows=6";
        if (LOG.isInfoEnabled()) {
            LOG.info("------李奇玉------URL值=" + URL + "," + "当前类=IndexService.queryAD1()");
        }

        try {
            String jsonData = apiService.doget(URL);
            if(LOG.isInfoEnabled()){
            LOG.info("------李奇玉------jsonData值=" + jsonData + "," + "当前类=IndexService.queryAD1()");
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (JsonNode row : rows) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("width", 670);
                map.put("height", 240);
                map.put("src", row.get("pic").asText());
                map.put("href", row.get("url").asText());
                map.put("alt", row.get("title").asText());
                map.put("widthB", 550);
                map.put("heightB", 240);
                map.put("srcB", row.get("pic").asText());
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
