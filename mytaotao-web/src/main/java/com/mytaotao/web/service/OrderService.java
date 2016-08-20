package com.mytaotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaotao.web.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by liqiyu on 2016/8/20 0020.
 */
@Service
public class OrderService {
    @Autowired
    ApiService apiService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String submit(Order order) throws IOException {
        String URL = "http://order.mytaotao.com/order/create";
        Map<Object, Object> objectObjectMap = apiService.doPostJson(URL, MAPPER.writeValueAsString(order));
        String jsonData = (String) objectObjectMap.get("data");
        JsonNode jsonNode = MAPPER.readTree(jsonData);
        String data = jsonNode.get("data").asText();
        return data;

    }

    public Order queryOrderById(Long id) throws IOException {
        String URL = "http://order.mytaotao.com/order/query/" + id;
        String jsonData = apiService.doget(URL);
        Order order = MAPPER.readValue(jsonData, Order.class);
        return order;
    }
}
