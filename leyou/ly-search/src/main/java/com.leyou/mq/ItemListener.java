package com.leyou.mq;

import com.leyou.constants.MQConstants;
import com.leyou.search.service.SearchService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.leyou.constants.MQConstants.Exchange.*;
import static com.leyou.constants.MQConstants.Queue.*;
import static com.leyou.constants.MQConstants.RoutingKey.*;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.mq
 * @ClassName: ItemListener
 * @Author: Dean
 * @Description: 消息监听器
 * @Date: 2019/6/26 10:04
 * @Version: 1.0
 */
@Component
public class ItemListener {
    @Autowired
    private SearchService searchService;

    /**
     * 上架商品就创建索引
     *
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = SEARCH_ITEM_UP, durable = "true"),
            exchange = @Exchange(name = ITEM_EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = SEARCH_ITEM_UP
    ))
    public void listenInsert(Long id) {
        if (id != null) {
            //新增索引
            searchService.createIndex(id);
        }
    }

    /**
     * 下架商品就删除索引
     *
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = SEARCH_ITEM_DOWN, durable = "false"),
            exchange = @Exchange(name = ITEM_EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = ITEM_DOWN_KEY
    ))
    public void listenDelete(Long id) {
        if (id != null) {
            //删除索引
            searchService.deleteIndex(id);
        }
    }

}
