package com.leyou.sms.mq;

import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import com.leyou.utils.JsonUtils;
import com.leyou.utils.RegexUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static com.leyou.constants.MQConstants.Exchange.SMS_EXCHANGE_NAME;
import static com.leyou.constants.MQConstants.Queue.SMS_VERIFY_CODE_QUEUE;
import static com.leyou.constants.MQConstants.RoutingKey.VERIFY_CODE_KEY;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.sms.mq
 * @ClassName: MessageListener
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 18:48
 * @Version: 1.0
 */
@Log4j
@Component
public class MessageListener {

    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties prop;

    /**
     * 监听消息 然后发送短信验证码
     *
     * @param map
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = SMS_VERIFY_CODE_QUEUE, durable = "true"),
            exchange = @Exchange(name = SMS_EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = VERIFY_CODE_KEY))
    public void sendListenMessageCode(Map<String, String> map) {

        if (!CollectionUtils.isEmpty(map)) {
//      不能抛异常不然 消息会一直发送过来
//        取出并且删除手机号
            String phone = map.remove("phone");
            if (!RegexUtils.isPhone(phone)) {
                // 手机号格式不正确
                log.error("手机号格式不正确:{}");
                return;
            }

            String parms = JsonUtils.toString(map);
            try {
//            发送短信
                smsUtils.sendMessage(phone, prop.getSignName(), prop.getVerifyCodeTemplate(), parms);
            } catch (Exception e) {
                log.error("短信发送失败!", e);
            }
        }
    }
}
