package test;

import com.leyou.UserApplication;
import com.leyou.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ProjectName: leyou
 * @Package: test
 * @ClassName: test1
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 22:21
 * @Version: 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class test1 {
    @Autowired
    private UserService userService;

    @Test
    public void sendMsg() {
        userService.sendCode("18961540176");
    }

}
