package com.leyou.order.service.impl;

import com.leyou.order.dto.OrderDTO;
import com.leyou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.order.service.impl
 * @ClassName: OrderServiceImpl
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/4 23:13
 * @Version: 1.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderDTO orderDTO) {
        System.out.println("orderDTO = " + orderDTO);

        return null;
    }
}
