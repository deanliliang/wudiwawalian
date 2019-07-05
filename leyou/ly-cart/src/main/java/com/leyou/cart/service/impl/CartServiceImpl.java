package com.leyou.cart.service.impl;

import com.leyou.cart.bean.Cart;
import com.leyou.cart.service.CartService;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.threadlocals.UserHolder;
import com.leyou.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.cart.service.impl
 * @ClassName: CartServiceImpl
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/4 20:20
 * @Version: 1.0
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private static final String CARTINDEX = "cart:uid:";
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 添加商品到购物车
     *
     * @param cart
     */
    @Override
    public void addCart(Cart cart) {
//        获取用户的uid
        String cartUidKey = CARTINDEX + UserHolder.getUser().getId();
        // 获取hash操作的对象，并绑定用户id
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartUidKey);
        // 获取商品id，作为hashKey
        String hashKey = cart.getSkuId().toString();
//        获取商品数量
        int num = cart.getNum();
//        在hashOps中查找 存在则修改
        Boolean aBoolean = hashOps.hasKey(hashKey);

        if (aBoolean != null && aBoolean) {
//            修改cart中的数量
            Cart cart1 = JsonUtils.toBean(hashOps.get(hashKey), Cart.class);
            cart.setNum(cart1.getNum() + num);
        }
//        写入redis
        hashOps.put(hashKey, JsonUtils.toString(cart));
    }

    /**
     * 查询用户购物车
     *
     * @return
     */
    @Override
    public List<Cart> queryCartList() {
        //        获取用户的uid
        String cartUidKey = CARTINDEX + UserHolder.getUser().getId();
//        查询购物车是否存在
        Boolean boo = redisTemplate.hasKey(cartUidKey);
        if (boo == null || !boo) {
            // 不存在，直接返回
            throw new LyException(ExceptionEnum.CARTS_NOT_FOUND);
        }
        // 获取hash操作的对象，并绑定用户id
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartUidKey);
        Long size = hashOps.size();
        if (size == null || size < 0) {
            // 不存在，直接返回
            throw new LyException(ExceptionEnum.CARTS_NOT_FOUND);
        }
        List<String> values = hashOps.values();
        List<Cart> cartList = values.stream().map(json ->
                JsonUtils.toBean(json, Cart.class)).collect(Collectors.toList());
        return cartList;
    }

    /**
     * 修改购物车商品数量
     *
     * @param id
     * @param num
     */
    @Override
    public void changeCartNum(Long id, Integer num) {
//        传入值不能为空
        if (id == null || num == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        //        获取当前用户的uid
        String Key = CARTINDEX + UserHolder.getUser().getId();
        // 获取hash操作的对象，并绑定用户id
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(Key);
        String s1 = id.toString();
        Boolean boo = hashOps.hasKey(s1);
        if (boo == null && !boo) {
            log.error("购物车商品不存在，用户：{}, 商品：{}", UserHolder.getUser().getId(), id);
            throw new LyException(ExceptionEnum.CARTS_NOT_FOUND);
        }
//        转换成cart并且设置新num
        Cart cart = JsonUtils.toBean(hashOps.get(s1), Cart.class);
        cart.setNum(num);
        //        写入redis
        hashOps.put(s1, JsonUtils.toString(cart));
    }

    /**
     * 删除购物车商品
     * @param id
     */
    @Override
    public void deleteCartGoods(Long id) {
//        id不能为空
        if (id==null){
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }

        String key =CARTINDEX + UserHolder.getUser().getId();
        // 获取hash操作的对象，并绑定用户id
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
        ops.delete(id.toString());
    }
}
