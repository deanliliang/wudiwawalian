package com.leyou.page.mq;

import com.leyou.page.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import javax.print.attribute.standard.MediaSize;

import static com.leyou.constants.MQConstants.Exchange.*;
import static com.leyou.constants.MQConstants.Queue.*;
import static com.leyou.constants.MQConstants.RoutingKey.*;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.page.mq
 * @ClassName: ItemListener
 * @Author: Dean
 * @Description: page监听器
 * @Date: 2019/6/26 10:47
 * @Version: 1.0
 */
@Component
public class ItemListener {

    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name =PAGE_ITEM_UP,durable = "true"),
            exchange =@Exchange(name = ITEM_EXCHANGE_NAME,type = ExchangeTypes.TOPIC),
            key = ITEM_UP_KEY
    ))
    public void listenInsert(Long id){
        if (id!=null){
            pageService.createHtmlItem(id);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = PAGE_ITEM_DOWN,durable = "false"),
            exchange = @Exchange(name = ITEM_EXCHANGE_NAME,type = ExchangeTypes.TOPIC),
            key = ITEM_DOWN_KEY
    ))
    public void listenDelete(Long id){
        if (id!=null){
            pageService.deleteHtmlItem(id);
        }
    }

}
