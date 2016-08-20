package com.mytaotao.store.order.mapper;

import com.mytaotao.store.order.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OrderMapper extends IMapper<Order>{
	
	void paymentOrderScan(@Param("date") Date date);

}
