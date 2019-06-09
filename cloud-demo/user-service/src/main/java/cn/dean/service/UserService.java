package cn.dean.service;

import cn.dean.bean.User;
import cn.dean.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: cloud-demo
 * @Package: cn.dean.service
 * @ClassName: UserService
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/9 20:03
 * @Version: 1.0
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
