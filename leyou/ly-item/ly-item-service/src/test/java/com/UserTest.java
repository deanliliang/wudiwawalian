package com;

import com.leyou.LyItemApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: leyou
 * @Package: com
 * @ClassName: UserTest
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 13:48
 * @Version: 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyItemApplication.class)
public class UserTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test() {
        ValueOperations<String, String> value = this.redisTemplate.opsForValue();
        value.set("123","qwe",20, TimeUnit.SECONDS);
    }

    @Test
    public void testSet() {
        BoundHashOperations<String, Object, Object> ops = this.redisTemplate.boundHashOps("user");
        ops.put("name","jack");
        ops.put("age","12");
        ops.put("age1","33");

        Map<Object, Object> entries = ops.entries();

        for (Map.Entry<Object, Object> object : entries.entrySet()) {
            System.out.println(object.getKey()+":"+object.getValue());

        }

    }
}
