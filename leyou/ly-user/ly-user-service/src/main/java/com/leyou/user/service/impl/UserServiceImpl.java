package com.leyou.user.service.impl;

import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.user.bean.User;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.service.UserService;
import com.leyou.utils.RegexUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.leyou.constants.MQConstants.Exchange.SMS_EXCHANGE_NAME;
import static com.leyou.constants.MQConstants.RoutingKey.VERIFY_CODE_KEY;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.user.service.impl
 * @ClassName: UserServiceImpl
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 20:16
 * @Version: 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    private static final String KEY_PREFIX = "verify:code:phone:";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Boolean checkUser(String data, Integer type) {
//        选择type是哪一个 switch  1: 用户名 2 : 手机号
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        查询数据库是否有数据 有数据返回false 不可注册
        int count = userMapper.selectCount(user);

        return count == 0;
    }

    /**
     * 发送验证码
     *
     * @param phone
     */
    @Override
    public void sendCode(String phone) {
//        判断手机号是否符合要求
        if (!RegexUtils.isPhone(phone)) {
            throw new LyException(ExceptionEnum.INVALID_PHONE_NUMBER);
        }
//        生成6位数随机验证码
        String numeric = RandomStringUtils.randomNumeric(6);
//        将数据加入到map中
        Map<String, String> map = new HashMap<>(10);
        map.put("code", numeric);
        map.put("phone", phone);
//        发送消息队列到交换机
        amqpTemplate.convertAndSend(SMS_EXCHANGE_NAME, VERIFY_CODE_KEY, map);
//        验证码放入到redis中保存时常 5分钟
//        先设置一个专门处理string的方法
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(KEY_PREFIX + phone, numeric, 5, TimeUnit.HOURS);

    }

    /**
     * 保存用户
     * @param user
     * @param code
     */
    @Override
    public void saveUser(User user, String code) {
//        判断验证码是否为空
        if(StringUtils.isBlank(code)){
            throw new LyException(ExceptionEnum.INVALID_VERIFY_CODE);
        }
        if(StringUtils.isBlank(user.getPhone())){
            throw new LyException(ExceptionEnum.INVALID_PHONE_NUMBER);
        }
        if(StringUtils.isBlank(user.getUsername())){
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

//        取出redis中的验证码 然后验证验证码时候一致
        String checkCode = redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if (!code.equalsIgnoreCase(checkCode)){
            throw new LyException(ExceptionEnum.INVALID_VERIFY_CODE);
        }

//        加密用户密码
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
//        保存用户
        int insert = userMapper.insert(user);
        if (insert==0){
            throw  new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 用户登陆
     * @param user
     */
    @Override
    public void login(User user) {

    }
}
