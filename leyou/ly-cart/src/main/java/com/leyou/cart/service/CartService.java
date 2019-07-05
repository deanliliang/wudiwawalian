package com.leyou.cart.service;

import com.leyou.cart.bean.Cart;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.cart.service
 * @ClassName: CartService
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/4 20:20
 * @Version: 1.0
 */
public interface CartService {
    void addCart(Cart cart);

    List<Cart> queryCartList();

    void changeCartNum(Long id, Integer num);

    void deleteCartGoods(Long id);
}
