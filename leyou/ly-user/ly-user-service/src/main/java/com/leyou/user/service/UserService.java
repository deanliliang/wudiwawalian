package com.leyou.user.service;

import com.leyou.user.bean.User;
import com.leyou.user.pojo.dto.UserDTO;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.user.service
 * @ClassName: UserService
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 20:15
 * @Version: 1.0
 */
public interface UserService {
    /**
     * 检验手机号 用户名
     * @param data
     * @param type
     * @return
     */
    Boolean checkUser(String data, Integer type);

    /**
     * 发送验证码
     * @param phone
     */
    void sendCode(String phone);

    /**
     * 保存用户
     * @param user
     * @param code
     */
    void saveUser(User user, String code);

    /**
     * 查询用户
     * @param username
     * @param password
     * @return
     */
    UserDTO queryUserByUsernameAndPassword(String username, String password);
}
