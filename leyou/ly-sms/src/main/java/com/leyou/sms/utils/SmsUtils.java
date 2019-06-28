package com.leyou.sms.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.sms.config.SmsProperties;
import com.leyou.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

import static com.leyou.sms.constants.SmsConstants.*;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.sms.utils
 * @ClassName: SmsUtil
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 14:55
 * @Version: 1.0
 */
@Slf4j
@Component
public class SmsUtils {
    @Autowired
    private IAcsClient client;
    @Autowired
    private SmsProperties prop;

    public SmsUtils(IAcsClient client, SmsProperties prop) {
        this.client = client;
        this.prop = prop;
    }
    public void sendMessage(String phone, String signName, String template, String param) {

        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(prop.getDomain());
        request.setVersion(prop.getVersion());
        request.setAction(prop.getAction());
        request.putQueryParameter(SMS_PARAM_KEY_PHONE, phone);
        request.putQueryParameter(SMS_PARAM_KEY_SIGN_NAME, signName);
        request.putQueryParameter(SMS_PARAM_KEY_TEMPLATE_CODE, template);
        request.putQueryParameter(SMS_PARAM_KEY_TEMPLATE_PARAM, param);
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map<String, String> map = JsonUtils.toMap(data, String.class, String.class);
            if (!OK.equalsIgnoreCase(map.get(SMS_RESPONSE_KEY_CODE))) {
//                发送失败
                log.error("[SMS服务]发送验证码失败,失败状态码:{},错误消息:{}",map.get(SMS_RESPONSE_KEY_CODE),map.get(SMS_RESPONSE_KEY_MESSAGE));
                throw new LyException(ExceptionEnum.SEND_MESSAGE_ERROR);
            }
            log.info("【SMS服务】发送短信成功，手机号：{}, 响应：{}", phone, response.getData());
        } catch (ServerException e) {
            log.error("[SMS服务]服务端异常,原因:{}",e.getMessage(),e);
            throw new LyException(ExceptionEnum.SEND_MESSAGE_ERROR);
        } catch (ClientException e) {
            log.error("[SMS服务]客户端异常,原因:{}",e.getMessage(),e);
            throw new LyException(ExceptionEnum.SEND_MESSAGE_ERROR);
        }
    }



}
