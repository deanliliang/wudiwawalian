package com.leyou.order.service;

import com.leyou.order.dto.OrderDTO;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.order.service
 * @ClassName: OrderService
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/4 23:13
 * @Version: 1.0
 */
public interface OrderService {
    Long createOrder(OrderDTO orderDTO);
}
