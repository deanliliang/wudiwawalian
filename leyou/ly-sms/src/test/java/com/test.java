package com;

import com.leyou.LySmsApplication;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import com.leyou.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com
 * @ClassName: test
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 16:30
 * @Version: 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySmsApplication.class)
public class test {

    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties prop;

    @Test
    public void send() {

        // 3、发送验证码，发送mq消息到ly-sms
        Map<String,String> msg =  new HashMap<>();
        msg.put("code", "12345678");
//        转为json发送数据
        String param = JsonUtils.toString(msg);
            smsUtils.sendMessage("18961540176",prop.getSignName(),prop.getVerifyCodeTemplate(), param);
    }
}
